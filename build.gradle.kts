import io.ktor.plugin.features.*
import org.gradle.internal.declarativedsl.parsing.main

plugins {
    id("java")
    id("application")
    id("org.graalvm.buildtools.native") version ("0.10.4")
    id("io.ktor.plugin") version "2.3.12"
}

group = "com.alex"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_23
java.targetCompatibility = JavaVersion.VERSION_23

repositories {
    mavenCentral()
}

dependencies {

    compileOnly("org.jetbrains:annotations:26.0.1")
    implementation("com.fasterxml.jackson.core:jackson-core:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.18.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.18.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.18.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.balatro.Balatro")
}

graalvmNative {
    binaries.all {
        resources.autodetect()
    }
}


ktor {
    fatJar {
        archiveFileName.set("balatro4j.jar")
    }
}
