package com.dovhan.netflix;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.dovhan.netflix");

        noClasses()
            .that()
                .resideInAnyPackage("com.dovhan.netflix.service..")
            .or()
                .resideInAnyPackage("com.dovhan.netflix.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.dovhan.netflix.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
