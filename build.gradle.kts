import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    kotlin("kapt") version "1.3.61"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))
    implementation(arrow("typeclasses"))
    implementation(arrow("extras-data"))
    implementation(arrow("syntax"))
    implementation(arrow("extras-extensions"))
    implementation(arrow("core-extensions"))

    testImplementation(arrow("test")) {
        exclude(module = "kotlin-reflect")
    }
    testRuntime(kotlin("reflect"))

    kapt(arrow("meta"))
}

fun DependencyHandler.arrow(module: String, version: String = "0.9.0"): String =
    "io.arrow-kt:arrow-$module:$version"

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.3"
        apiVersion = "1.3"
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}