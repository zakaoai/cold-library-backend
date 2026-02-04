plugins {
	id("org.springframework.boot") version "4.0.2"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm") version "2.3.0"
	kotlin("plugin.spring") version "2.3.0"
	id("org.springdoc.openapi-gradle-plugin") version "1.9.0"
	id ("jacoco")
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
	implementation("org.springframework.security:spring-security-oauth2-resource-server:7.0.2")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.21.0")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.3.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.10.2")
	implementation("org.liquibase:liquibase-core:5.0.1")
	implementation("org.springframework:spring-jdbc:7.0.3")
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:3.0.1")
	testImplementation("org.springdoc:springdoc-openapi-starter-webflux-api:3.0.1")
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.postgresql:r2dbc-postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "mockito-core")
	}
	testImplementation("io.projectreactor:reactor-test:3.8.2")
	testImplementation("org.springframework.security:spring-security-test:7.0.2")
	// https://mvnrepository.com/artifact/io.mockk/mockk
	testImplementation("io.mockk:mockk:1.14.9")
	testImplementation("com.ninja-squad:springmockk:5.0.1")
	testImplementation ("io.rest-assured:spring-mock-mvc:6.0.0")
	// https://mvnrepository.com/artifact/io.rest-assured/spring-mock-mvc-kotlin-extensions
	testImplementation("io.rest-assured:spring-mock-mvc-kotlin-extensions:6.0.0")

	implementation("com.github.zakaoai:NyaaSi-API:1.0.2")
	implementation("org.springframework.session:spring-session-core:4.0.1")
	implementation("com.auth0:auth0:3.1.0")
	implementation("com.github.zakaoai:reactive-jikan:feature~pagination-SNAPSHOT")
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
