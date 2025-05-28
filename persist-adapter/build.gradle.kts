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
    implementation(libs.mybatis.spring.boot.starter)
    {
        exclude(group = "ch.qos.logback", module = "logback-classic")
        exclude(group = "org.springframework.boot", module = "spring-boot-autoconfigure")
        exclude(group = "org.springframework", module = "spring-context")
        exclude(group = "org.yaml", module = "snakeyaml")
    }
//    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.org.springframework.spring.context)
//    implementation(libs.oceanframework.web)
    compileOnly(libs.org.projectlombok)
    annotationProcessor(libs.org.projectlombok)
    testCompileOnly(libs.org.projectlombok)
    testAnnotationProcessor(libs.org.projectlombok)

    implementation(project(":port-persist"))
}


tasks.withType<Test> {
    useJUnitPlatform()
}

