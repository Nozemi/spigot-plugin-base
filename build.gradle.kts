import kr.entree.spigradle.kotlin.*
import de.undercouch.gradle.tasks.download.Download
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val spigotVersion: String by project
val pluginApiVersion: String by project
val pluginVersion: String by project
val pluginName: String by project

buildscript {
    repositories {
        mavenCentral()
        maven(uri("https://plugins.gradle.org/m2/"))
    }

    dependencies {
        classpath("gradle.plugin.com.nemosw:spigot-jar:1.0")
        classpath("dev.alangomes:spigot-spring-boot-starter:0.20.3")
    }
}

plugins {
    kotlin("jvm") version "1.3.70"
    id("kr.entree.spigradle") version "1.2.4"
    id("de.undercouch.download") version "4.0.4"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(spigot(spigotVersion))

    compileOnly("org.projectlombok:lombok:1.18.10")
    annotationProcessor("org.projectlombok:lombok:1.18.10")

    implementation("dev.alangomes:spigot-spring-boot-starter:0.20.4")
    implementation(kotlin("stdlib-jdk8"))
}

spigot {
    authors = listOf("Nozemi")
    name = pluginName
    version = pluginVersion
    apiVersion = pluginApiVersion
    load = kr.entree.spigradle.attribute.Load.STARTUP
    commands {
        create("heal") {
            description = "A heal command"
            permission = "${pluginName.toLowerCase()}.heal"
            usage = "/heal"
        }
    }
    permissions {
        create("${pluginName.toLowerCase()}.heal") {
            description = "Allows players to use the heal command"
            defaults = "true"
        }
    }
}

tasks {

    register<Delete>("cleanProject") {
        group = "spigot-plugin"

        dependsOn(":clean")

        delete(file("./testserver"))
    }

    register<Jar>("fatJar") {
        group = "spigot-plugin"

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(configurations.runtimeClasspath.get()
                .onEach { println("add from dependencies: ${it.name}") }
                .map { if (it.isDirectory) it else zipTree(it) })

        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        from(sourcesMain.output)
    }

    register<Download>("downloadSpigotServer") {
        group = "spigot-plugin"

        src("https://cdn.getbukkit.org/spigot/spigot-$spigotVersion.jar")
        dest("./testserver/spigot-$spigotVersion.jar")
        overwrite(false)
    }

    register<Copy>("setupTestServer") {
        group = "spigot-plugin"

        dependsOn(":downloadSpigotServer")

        from(file("./config/devserver"))
        into(file("./testserver"))
    }

    register<Copy>("copyPluginToTestServer") {
        group = "spigot-plugin"

        dependsOn(":cleanProject", ":fatJar", ":setupTestServer")

        from(file("./build/libs/$pluginName.jar"))
        into(file("./testserver/plugins"))
    }

    register<JavaExec>("runTestServer") {
        group = "spigot-plugin"

        dependsOn(":copyPluginToTestServer", ":downloadSpigotServer")

        classpath(files("./testserver/spigot-$spigotVersion.jar"))
        workingDir("./testserver/")
        standardInput = System.`in`
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}