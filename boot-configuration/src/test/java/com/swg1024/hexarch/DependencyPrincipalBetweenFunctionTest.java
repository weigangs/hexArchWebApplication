package com.swg1024.hexarch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class DependencyPrincipalBetweenFunctionTest {

    private final JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.swg1024.hexarch");

    private static final String baseJavaPackage = "java..";
    private static final String jakartaPackage = "jakarta..";

    private static final String springFrameworkPackage = "org.springframework..";

    private static final String oceanFrameworkPackage = "com.lkyl.oceanframework..";

    private static final String mapStructPackage = "org.mapstruct..";

    private static final String ibatisPackage = "org.apache.ibatis..";

    private static final String persistAdapterEntityPackage = "com.swg1024.hexarch.adapter.persist.entity..";

    private static final String persistAdapterMapperPackage = "com.swg1024.hexarch.adapter.persist.mapper..";

    private static final String applicationBasePackage = "com.swg1024.hexarch.application.";

    private static final String domainBasePackage = "com.swg1024.hexarch.domain.";

    private static final String webPortBasePackage = "com.swg1024.hexarch.port.web.";

    private static final String webAdapterBasePackage = "com.swg1024.hexarch.adapter.web.";

    private static final String persistAdapterBasePackage = "com.swg1024.hexarch.adapter.persist.";

    private static final String persistPortBasePackage = "com.swg1024.hexarch.port.persist.";

    public ArchRule webAdapterOnlyDependentOnPortWeb(String moduleName) {
        return classes()
                .that()
                .resideInAPackage(
                        webAdapterBasePackage + moduleName + ".."

                )
                .should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        webAdapterBasePackage + moduleName + "..",
                        webPortBasePackage + moduleName + ".."

                ).or(resideInAnyPackage(baseJavaPackage,jakartaPackage,springFrameworkPackage,oceanFrameworkPackage,mapStructPackage)))
                .because("webAdapter module cannot access classes outside webAdapter,webPort modules");
    }

    public ArchRule webPortNotDependentOnOtherPackage(String moduleName) {
        return classes()
                .that()
                .resideInAPackage(
                        webPortBasePackage + moduleName + ".."

                )
                .should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        webPortBasePackage + moduleName + ".."

                ).or(resideInAnyPackage(baseJavaPackage,jakartaPackage,springFrameworkPackage,oceanFrameworkPackage)))
                .because("webPort module cannot access classes outside current package");
    }


    public ArchRule applicationDependentOnSameModuleNameInAndOutAndDomain(String moduleName) {
        return classes()
                .that()
                .resideInAPackage(
                        applicationBasePackage + moduleName + ".."

                )
                .should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        applicationBasePackage + moduleName + "..",
                        domainBasePackage + moduleName + "..",
                        webPortBasePackage + moduleName + "..",
                        persistPortBasePackage + moduleName + ".."

                ).or(resideInAnyPackage(baseJavaPackage,springFrameworkPackage,oceanFrameworkPackage,mapStructPackage)))
                .because("application module cannot access classes outside application,domain,webPort,persistPort modules");
    }

    public ArchRule domainDependentOnPort(String moduleName) {
        return classes()
                .that()
                .resideInAPackage(
                        domainBasePackage + moduleName + ".."

                )
                .should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        domainBasePackage + moduleName + "..",
                        webPortBasePackage + moduleName + "..",
                        persistPortBasePackage + moduleName + ".."

                ).or(resideInAnyPackage(baseJavaPackage,oceanFrameworkPackage)))
                .because("application module cannot access classes outside application,domain,webPort,persistPort modules");
    }


    public ArchRule persistPortNotDependentOnOtherPackage(String moduleName) {
        return classes()
                .that()
                .resideInAPackage(
                        persistPortBasePackage + moduleName + ".."

                )
                .should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        persistPortBasePackage + moduleName + ".."

                ).or(resideInAnyPackage(baseJavaPackage,jakartaPackage,springFrameworkPackage,oceanFrameworkPackage)))
                .because("persistPort module cannot access classes outside current package");
    }

    public ArchRule persistAdapterOnlyDependentOnPersistPort(String moduleName) {
        return classes()
                .that()
                .resideInAPackage(
                        persistAdapterBasePackage + moduleName + ".."

                ).should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        persistAdapterBasePackage + moduleName + "..",
                        persistPortBasePackage  + moduleName + ".."
                ).or(resideInAnyPackage(baseJavaPackage,jakartaPackage,springFrameworkPackage,oceanFrameworkPackage
                        ,mapStructPackage,ibatisPackage,persistAdapterEntityPackage,persistAdapterMapperPackage)))
                .because("persistPort module cannot access classes outside current package");
    }


    @Test
    public void checkAllFunctions() {
        for (String moduleName : findAllModuleNames("com.swg1024.hexarch", "application")) {
            applicationDependentOnSameModuleNameInAndOutAndDomain(moduleName).check(importedClasses);

        }

        for (String moduleName : findAllModuleNames("com.swg1024.hexarch", "domain")) {
            domainDependentOnPort(moduleName).check(importedClasses);
        }

        for (String moduleName : findAllModuleNames("com.swg1024.hexarch", "adapter.web")) {
            webAdapterOnlyDependentOnPortWeb(moduleName).check(importedClasses);


        }
        for (String moduleName : findAllModuleNames("com.swg1024.hexarch", "port.web")) {
            webPortNotDependentOnOtherPackage(moduleName).check(importedClasses);

        }
        for (String moduleName : findAllModuleNames("com.swg1024.hexarch", "port.persist")) {
            persistPortNotDependentOnOtherPackage(moduleName).check(importedClasses);

        }
        for (String moduleName : findAllModuleNames("com.swg1024.hexarch", "port.persist")) {
            persistPortNotDependentOnOtherPackage(moduleName).check(importedClasses);

        }
        for (String moduleName : findAllModuleNames("com.swg1024.hexarch", "adapter.persist")) {
            persistAdapterOnlyDependentOnPersistPort(moduleName).check(importedClasses);

        }



    }

    public Set<String> findAllModuleNames(String basePackage, String layer) {
        try (var scanResult = new ClassGraph()
                .acceptPackages(basePackage + "." + layer)
                .scan()) {
            return scanResult.getAllClasses()
                    .stream()
                    .map(ClassInfo::getPackageName)
                    .filter(pkg -> pkg.startsWith(basePackage + "." + layer + "."))
                    .map(pkg -> pkg.replace(basePackage + "." + layer + ".", "").split("\\.")[0])
                    .collect(Collectors.toSet());
        }
    }

    @Test
    public void getAllModule() {
        findAllModuleNames("com.swg1024.hexarch", "adapter.web").forEach(System.out::println);
    }
}
