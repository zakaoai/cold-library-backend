import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
	id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
	id ("jacoco")
}

group = "fr.zakaoai"
version = "1.4.0-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
	jvmToolchain(17)
}


repositories {
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
	implementation("org.springframework.security:spring-security-oauth2-resource-server:6.2.0")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.liquibase:liquibase-core")
	implementation("org.springframework:spring-jdbc")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")
// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webflux-api
	testImplementation("org.springdoc:springdoc-openapi-starter-webflux-api:2.3.0")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("io.r2dbc:r2dbc-h2")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.postgresql:r2dbc-postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "mockito-core")
	}
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.security:spring-security-test")
	// https://mvnrepository.com/artifact/io.mockk/mockk
	testImplementation("io.mockk:mockk:1.13.8")
	testImplementation("com.ninja-squad:springmockk:4.0.2")

	// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.0")
	implementation("net.sandrohc:reactive-jikan:2.2.0")
	implementation("com.github.zakaoai:NyaaSi-API:1.0.1")

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