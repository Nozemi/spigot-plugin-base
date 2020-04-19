buildscript {
    repositories {
        mavenCentral()
        maven(uri("https://plugins.gradle.org/m2/"))
    }

    val springVersion = "2.2.6.RELEASE"
    dependencies {
        classpath("gradle.plugin.com.nemosw:spigot-jar:1.0")
        classpath("dev.alangomes:spigot-spring-boot-starter:0.20.3")
    }
}

tasks {

    register<Copy>("setupTestServer") {
        group = "sjokkcraft"

        from(file("./config/serverfiles"))
        into(file("./testserver"))
    }

    register<Delete>("cleanTestServer") {
        group = "sjokkcraft"

        delete(file("./testserver"))
    }

    register<Copy>("copyPluginToTestServer") {
        group = "sjokkcraft"

        dependsOn(":cleanTestServer", ":fatJar", ":setupTestServer")

        from(file("./build/libs/SjokkCraft.jar"))
        into(file("./testserver/plugins"))
    }

    register<JavaExec>("runTestServer") {
        group = "sjokkcraft"

        dependsOn(":copyPluginToTestServer")

        classpath(files("./testserver/spigot.jar"))
        workingDir("./testserver/")
        standardInput = System.`in`
    }
}