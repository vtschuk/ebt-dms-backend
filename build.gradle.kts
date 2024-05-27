import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.5"
    //id("org.jetbrains.kotlin.plugin.spring") version "2.0.0"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.jpa") version "1.9.24"
    //kotlin("plugin.allopen") version "2.0.0"
    kotlin("plugin.noarg") version "1.9.24"
}

group = "de.ass73.ebt.dms.backend"
version = "0.0.1-SNAPSHOT"

/*
sourceSets {
    main.java.srcDirs += 'src/main/kotlin/'
    test.java.srcDirs += 'src/test/kotlin/'
}
 */

allOpen {
    annotation("de.ass73.ebt.dms.backend.models.NoArgs")
    // annotations("com.another.Annotation", "com.third.Annotation")
}

noArg {
    annotation("de.ass73.ebt.dms.backend.models.NoArgs")
}

noArg {
    invokeInitializers = true
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    //implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.modelmapper:modelmapper:3.2.0")
    implementation("io.jsonwebtoken:jjwt:0.12.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.12.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.5")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
     //testImplementation("org.mockito:mockito-core:5.12.0")
    //testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
