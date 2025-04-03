import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
    id("jacoco")
}

group = "fr.zakaoai"
version = "2.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}


repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://jcenter.bintray.com")
    }
    maven {
        url = uri("https://jitpack.io")
    }

}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
// https://mvnrepository.com/artifact/org.springframework.security/spring-security-oauth2-resource-server
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework:spring-jdbc")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.6")
// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webflux-api
    testImplementation("org.springdoc:springdoc-openapi-starter-webflux-api:2.8.6")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
    // https://mvnrepository.com/artifact/io.mockk/mockk
    testImplementation("io.mockk:mockk:1.13.17")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("io.rest-assured:spring-mock-mvc")
    // https://mvnrepository.com/artifact/io.rest-assured/spring-mock-mvc-kotlin-extensions
    testImplementation("io.rest-assured:spring-mock-mvc-kotlin-extensions")

    // https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
    implementation("com.github.zakaoai:reactive-jikan:feature~pagination-SNAPSHOT")

    implementation("com.github.zakaoai:NyaaSi-API:1.0.2")

    // https://mvnrepository.com/artifact/org.springframework.session/spring-session-core
    implementation("org.springframework.session:spring-session-core")

    implementation("com.auth0:auth0:2.19.0")


}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
    dependsOn(tasks.test) // tests are required to run before generating the report
}

tasks.jar {
    enabled = false
}