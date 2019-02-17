import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.21"
    application
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

//version = "1.0-SNAPSHOT"

application {
    mainClassName = "azure.tika.FunctionKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.microsoft.azure:azure-functions-java-core:1.0.0-beta-2")
    //implementation("org.apache.tika:tika-parsers:1.16")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

