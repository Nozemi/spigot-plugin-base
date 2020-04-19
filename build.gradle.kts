import kr.entree.spigradle.kotlin.*
//import org.springframework.boot.gradle.tasks.bundling.*

val springVersion = "2.2.6.RELEASE"

plugins {
    kotlin("jvm") version "1.3.70"
    id("kr.entree.spigradle") version "1.2.4"
}

apply(from = "gradle/spigot.gradle.kts")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(spigot("1.14.4"))

    compileOnly("org.projectlombok:lombok:1.18.10")
    annotationProcessor("org.projectlombok:lombok:1.18.10")

    implementation("dev.alangomes:spigot-spring-boot-starter:0.20.4")
}

//tasks.getByName<BootJar>("bootJar") {
//    mainClassName = "io.sjokkcraft.minecraft.SjokkCraft"
//}

spigot {
    authors = listOf("Nozemi")
    version = "0.1.0.0"
    apiVersion = "1.14"
    load = kr.entree.spigradle.attribute.Load.STARTUP
    commands {
        create("test") {
            aliases = listOf("t")
            description = "A test command"
            permission = "sjokkcraft.test"
            usage = "/<command>"
        }
    }
    permissions {
        create("sjokkcraft.test") {
            description = "Allows players to use test command"
            defaults = "true"
        }
    }
}

tasks.register<Jar>("fatJar") {
    group = "sjokkcraft"

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })

    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
}