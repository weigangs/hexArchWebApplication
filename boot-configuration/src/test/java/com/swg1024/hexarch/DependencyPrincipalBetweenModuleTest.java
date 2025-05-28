package com.swg1024.hexarch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;

public class DependencyPrincipalBetweenModuleTest {

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

    private static final String webPortModule = "com.swg1024.hexarch.port.web..";

    private static final String persistPortModule = "com.swg1024.hexarch.port.persist..";

    private static final String domainModule = "com.swg1024.hexarch.domain..";

    private static final String applicationModule = "com.swg1024.hexarch.application..";

    private static final String webAdapterModule = "com.swg1024.hexarch.adapter.web..";

    private static final String persistAdapterModule = "com.swg1024.hexarch.adapter.persist..";

    @Test
    void webAdapterShouldNotBeDependedOnByApplicationOrDomain() {
        ArchRuleDefinition.noClasses()
                .that().resideInAnyPackage(webPortModule, persistPortModule, domainModule, applicationModule)
                .should().dependOnClassesThat().resideInAnyPackage(webAdapterModule)
                .check(importedClasses);
    }

    @Test
    void inPortShouldNotDependedOnAnyModule() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage(webPortModule)
                .should().dependOnClassesThat().resideInAnyPackage(webAdapterModule,persistAdapterModule,applicationModule,domainModule, persistPortModule)
                .check(importedClasses);
    }

    @Test
    void domainShouldOnlyDependOnOutPackages() {
        ArchRuleDefinition.classes()
                .that().resideInAPackage(domainModule)
                .should().onlyDependOnClassesThat(resideInAnyPackage(
                        webPortModule, persistPortModule).or(resideInAPackage(baseJavaPackage)))
                .check(importedClasses);
    }

    @Test
    void applicationShouldOnlyDependOnDomain() {
        ArchRuleDefinition.classes()
                .that().resideInAPackage(applicationModule)
                .should().onlyDependOnClassesThat(resideInAnyPackage(
                        applicationModule, domainModule, webPortModule, persistPortModule)
                        .or(resideInAnyPackage(baseJavaPackage,springFrameworkPackage,oceanFrameworkPackage,mapStructPackage)))
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
