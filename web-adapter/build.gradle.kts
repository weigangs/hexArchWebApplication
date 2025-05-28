plugins {
    id("java-library")
    id("buildlogic.java-application-conventions")
}



java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

apply(plugin = "com.swg1024.hexarch.publishing")

dependencies {
    api(libs.org.springframework.boot.spring.boot.starter.web)
    implementation(libs.oceanframework.web)
    compileOnly(libs.org.projectlombok)
    annotationProcessor(libs.org.projectlombok)
    testCompileOnly(libs.org.projectlombok)
    testAnnotationProcessor(libs.org.projectlombok)
    implementation(project(":port-web"))

}


tasks.withType<Test> {
    useJUnitPlatform()
}

