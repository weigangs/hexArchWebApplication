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

    private static final String [] isInAllowedPackages = {
            "java..","jakarta..", "org.springframework..","com.lkyl.oceanframework..", "org.mapstruct.."
    };

    private static final String baseJavaPackage = "java..";
    private static final String jakartaPackage = "jakarta..";

    private static final String springFrameworkPackage = "org.springframework..";

    private static final String oceanFrameworkPackage = "com.lkyl.oceanframework..";

    private static final String mapStructPackage = "org.mapstruct..";

//    private static final String [] isInAllowedPackagesForPersistAdapter = {
//            "java..","jakarta..", "org.springframework..","com.lkyl.oceanframework..", "org.mapstruct..",
//            "com.swg1024.hexarch.adapter.persist.entity..", "com.swg1024.hexarch.adapter.persist.mapper..",
//            "com.swg1024.hexarch.port.in.."
//    };

    public ArchRule webAdapterDependentOnSameModuleNameIn(String moduleName) {
        return classes()
                .that()
                .resideInAPackage(
                        "com.swg1024.hexarch.adapter.web." + moduleName + ".."

                )
                .should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        "com.swg1024.hexarch.port.in." + moduleName + ".."

                ).or(resideInAnyPackage(baseJavaPackage,jakartaPackage,springFrameworkPackage)))
                .because("except in-port module, other modules cannot access classes inside " + moduleName);
    }



    public ArchRule applicationDependentOnSameModuleNameInAndOutAndDomain(String moduleName) {
        return classes()
                .that()
                .resideInAPackage(
                        "com.swg1024.hexarch.application." + moduleName + ".."

                )
                .should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        "com.swg1024.hexarch.domain." + moduleName + "..",
                        "com.swg1024.hexarch.application." + moduleName + ".converter",
                        "com.swg1024.hexarch.port.in." + moduleName + "..",
                        "com.swg1024.hexarch.port.out." + moduleName + ".."

                ).or(resideInAnyPackage(isInAllowedPackages)))
                .because("except in-port module, other modules cannot access classes inside " + moduleName);
    }


    @Test
    public void domainShouldNotDependOnOtherPackages() {
        applicationDependentOnSameModuleNameInAndOutAndDomain("queryUser").check(importedClasses);
        applicationDependentOnSameModuleNameInAndOutAndDomain("createUser").check(importedClasses);
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
        findAllModuleNames("com.swg1024.hexarch", "application").forEach(System.out::println);
    }
}
