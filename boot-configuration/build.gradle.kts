plugins {
    id("buildlogic.java-application-conventions")
    id("org.springframework.boot") version "3.3.10"
    id("io.spring.dependency-management") version "1.1.4"
    id("java")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

apply(plugin = "com.swg1024.hexarch.publishing")

dependencies{
    implementation(libs.org.springframework.boot.starter)
    implementation(libs.mybatis.spring.boot.starter) {
        exclude(group = "ch.qos.logback", module = "logback-classic")
        exclude(group = "org.springframework.boot", module = "spring-boot-autoconfigure")
        exclude(group = "org.springframework", module = "spring-context")
        exclude(group = "org.yaml", module = "snakeyaml")
    }
    implementation("com.h2database:h2:2.2.224")
    implementation(project(":application"))
    implementation(project(":port-web"))
    implementation(project(":port-persist"))
    implementation(project(":web-adapter"))
    implementation(project(":persist-adapter"))
    implementation(project(":domain-logic"))
    compileOnly(libs.org.projectlombok)
    annotationProcessor(libs.org.projectlombok)
    testCompileOnly(libs.org.projectlombok)
    testAnnotationProcessor(libs.org.projectlombok)
    testImplementation(libs.archunit.junit5)
    testImplementation(libs.archunit)
    testImplementation(libs.classgraph)
}


tasks.withType<Test> {
    useJUnitPlatform()
}

springBoot {
    mainClass.set("com.swg1024.hexarch.BootApplication")
}

tasks.test {
    reports.html.required.set(false)
    reports.junitXml.required.set(false)
}

