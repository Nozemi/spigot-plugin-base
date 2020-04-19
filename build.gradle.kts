import kr.entree.spigradle.kotlin.*
import de.undercouch.gradle.tasks.download.Download

val spigotVersion: String by project
val pluginApiVersion: String by project
val pluginVersion: String by project

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
}

spigot {
    authors = listOf("Nozemi")
    version = pluginVersion
    apiVersion = pluginApiVersion
    load = kr.entree.spigradle.attribute.Load.STARTUP
    commands {
        create("heal") {
            aliases = listOf("t")
            description = "A heal command"
            permission = "sjokkcraft.heal"
            usage = "/<command>"
        }
    }
    permissions {
        create("sjokkcraft.heal") {
            description = "Allows players to use the heal command"
            defaults = "true"
        }
    }
}

tasks {

    register<Delete>("cleanProject") {
        group = "sjokkcraft"

        dependsOn(":clean")

        delete(file("./testserver"))
    }

    register<Jar>("fatJar") {
        group = "sjokkcraft"

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(configurations.runtimeClasspath.get()
                .onEach { println("add from dependencies: ${it.name}") }
                .map { if (it.isDirectory) it else zipTree(it) })

        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        from(sourcesMain.output)
    }

    register<Download>("downloadSpigotServer") {
        group = "sjokkcraft"

        src("https://cdn.getbukkit.org/spigot/spigot-$spigotVersion.jar")
        dest("./testserver/spigot-$spigotVersion.jar")
        overwrite(false)
    }

    register<Copy>("setupTestServer") {
        group = "sjokkcraft"

        dependsOn(":downloadSpigotServer")

        from(file("./config/devserver"))
        into(file("./testserver"))
    }

    register<Copy>("copyPluginToTestServer") {
        group = "sjokkcraft"

        dependsOn(":cleanProject", ":fatJar", ":setupTestServer")

        from(file("./build/libs/SjokkCraft.jar"))
        into(file("./testserver/plugins"))
    }

    register<JavaExec>("runTestServer") {
        group = "sjokkcraft"

        dependsOn(":copyPluginToTestServer", ":downloadSpigotServer")

        classpath(files("./testserver/spigot-$spigotVersion.jar"))
        workingDir("./testserver/")
        standardInput = System.`in`
    }
}
