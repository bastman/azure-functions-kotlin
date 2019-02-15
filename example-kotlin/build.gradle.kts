import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.21"
    application
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

version = "1.0-SNAPSHOT"

application {
    mainClassName = "azure.tika.FunctionKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.microsoft.azure:azure-functions-java-core:1.0.0-beta-3")
    implementation("org.apache.tika:tika-parsers:1.16")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val azureOutputDir = "$buildDir/azure-functions/"
println("=== azureOutputDir: $azureOutputDir")



val libsDir:File = property("libsDir") as File
val copyJar = tasks.create<Copy>("copyJar") {
    println(this.name)
    println("==== LIBS_DIR: $libsDir")
    group = "azure"
    copy {
        from("$libsDir/${rootProject.name}-all.jar")
        into(azureOutputDir)
        rename {fileName ->
            fileName.replace("${rootProject.name}-all", "function")
        }
        include("**/function.json")
    }
}

copyJar.dependsOn("build")

tasks.create<Copy>("copyFunctionDefs") {
    println(this.name)
    group = "azure"
    copy {
        from("src/main/resources/functions")
        into(azureOutputDir)
        include("**/function.json")
    }
}
tasks.create<Copy>("copyOpenApiDef") {
    println(this.name)
    group = "azure"
    copy {
        from("src/main/resources")
        into(azureOutputDir)
        include("swagger.*")
    }
}
/*
tasks.create<Delete>("cleanAzureFunction") {
    println(this.name)
    group = "azure"
    delete = setOf (azureOutputDir)
}
*/
val packageAzureFunction = tasks.create<Copy>("packageAzureFunction") {
    println(this.name)
    println("$rootDir -> $azureOutputDir")
    group = "azure"
    copy {
        from(rootDir)
        into(azureOutputDir)
        include("host.json", "local.settings.json")
    }
}
//packageAzureFunction.dependsOn("cleanAzureFunction", "build", "copyJar", "copyFunctionDefs", "copyOpenApiDef")
packageAzureFunction.dependsOn("build", "copyJar", "copyFunctionDefs", "copyOpenApiDef")


//defaultTasks("run")