package com.swg1024.hexarch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;

public class HexagonalArchitectureTest {

    private final JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.swg1024.hexarch");

    private static final String [] isInAllowedPackages = {
            "java..","jakarta..", "org.springframework..","com.lkyl.oceanframework..", "org.mapstruct.."
    };

    @Test
    void domainShouldNotDependOnOtherPackages() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..application..", "..adapter..")
                .check(importedClasses);
    }

    @Test
    void applicationShouldOnlyDependOnDomain() {
        ArchRuleDefinition.classes()
                .that().resideInAPackage("..application..")
                .should().onlyDependOnClassesThat(resideInAnyPackage(
                        "..application..", "..domain..", "..port.in..", "..port.out..")
                        .or(resideInAnyPackage(isInAllowedPackages)))
                .check(importedClasses);
    }

    @Test
    void adapterShouldNotBeDependedOnByApplicationOrDomain() {
        ArchRuleDefinition.noClasses()
                .that().resideInAnyPackage("..domain..", "..application..")
                .should().dependOnClassesThat().resideInAnyPackage("..adapter..")
                .check(importedClasses);
    }

    @Test
    void adaptersShouldBeIndependentFromEachOther() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..adapter.web..")
                .should().dependOnClassesThat().resideInAnyPackage("..adapter.persist..")
                .check(importedClasses);

        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..adapter.persist..")
                .should().dependOnClassesThat().resideInAnyPackage("..adapter.web..")
                .check(importedClasses);
    }
}
