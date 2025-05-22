package com.swg1024.hexarch.createUser;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClass;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class CreateUserFuncBoundaryTest {

    private static final String [] isInAllowedPackages = {
            "java..","jakarta..", "org.springframework..","com.lkyl.oceanframework..", "org.mapstruct..",
            "com.swg1024.hexarch.adapter.persist.entity..", "com.swg1024.hexarch.adapter.persist.mapper..",
            "com.swg1024.hexarch.port.in.."
    };

    private final JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.swg1024.hexarch");

    public ArchRule applicationOnlyAllowAccessPortInAndPortOutAndDomain(String moduleName) {
        return classes()
                .that()
                .resideInAPackage(
                        "com.swg1024.hexarch.application." + moduleName + ".."

                )
                .should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        "com.swg1024.hexarch.domain." + moduleName + "..",
                        "com.swg1024.hexarch.port.out." + moduleName + ".."

                ).or(resideInAnyPackage(isInAllowedPackages)))
                .because("except in-port module, other modules cannot access classes inside " + moduleName);
    }
//
//    public ArchRule applicationOnlyAllowAccessPortInAndPortOutAndDomain(String moduleName) {
//        return classes()
//                .that()
//                .resideInAPackage(
//                        "com.swg1024.hexarch.application." + moduleName + ".."
//
//                )
//                .should()
//                .onlyAccessClassesThat(resideInAnyPackage(
//                        "com.swg1024.hexarch.domain." + moduleName + "..",
//                        "com.swg1024.hexarch.port.out." + moduleName + ".."
//
//                ).or(resideInAnyPackage(isInAllowedPackages)))
//                .because("except in-port module, other modules cannot access classes inside " + moduleName);
//    }

    @Test
    public void domainShouldNotDependOnOtherPackages() {
        applicationOnlyAllowAccessPortInAndPortOutAndDomain("queryUser").check(importedClasses);
        applicationOnlyAllowAccessPortInAndPortOutAndDomain("createUser").check(importedClasses);
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
