package com.swg1024.hexarch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import org.junit.jupiter.api.Test;

import javax.annotation.processing.Generated;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

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

    private static final String inPortModule = "..port.in..";

    private static final String outPortModule = "..port.out..";

    private static final String domainModule = "..domain..";

    private static final String applicationModule = "..application..";

    private static final String webAdapterModule = "..adapter.web..";

    private static final String persistAdapterModule = "..adapter.persist..";

    @Test
    void webAdapterShouldNotBeDependedOnByApplicationOrDomain() {
        ArchRuleDefinition.noClasses()
                .that().resideInAnyPackage(inPortModule, outPortModule, domainModule, applicationModule)
                .should().dependOnClassesThat().resideInAnyPackage(webAdapterModule)
                .check(importedClasses);
    }

    @Test
    void inPortShouldNotDependedOnAnyModule() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage(inPortModule)
                .should().dependOnClassesThat().resideInAnyPackage(webAdapterModule)
                .check(importedClasses);
    }

    @Test
    void domainShouldOnlyDependOnOutPackages() {
        ArchRuleDefinition.classes()
                .that().resideInAPackage(domainModule)
                .should().onlyDependOnClassesThat(resideInAPackage(
                        outPortModule).or(resideInAPackage(baseJavaPackage)))
                .check(importedClasses);
    }

    @Test
    void applicationShouldOnlyDependOnDomain() {
        ArchRuleDefinition.classes()
                .that().resideInAPackage(applicationModule)
                .and().areNotAnnotatedWith(Generated.class)
                .should().onlyDependOnClassesThat(resideInAnyPackage(
                        domainModule, inPortModule, outPortModule)
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
