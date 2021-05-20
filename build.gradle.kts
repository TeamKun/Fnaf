plugins {
    java
    kotlin("jvm").version(Dependencies.Kotlin.version)
    kotlin("kapt").version(Dependencies.Kotlin.version)
}

group = "com.reyadayer"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven(Dependencies.Spigot.repository)
    maven(Dependencies.Paper.repository)
    maven(Dependencies.SonaType.repository)
    maven(Dependencies.ProtocolLib.repository)
    maven(Dependencies.MockBukkit.repository)
    maven(Dependencies.Exposed.repository)
}

dependencies {
    compile(Dependencies.Spigot.api)
    compileOnly(Dependencies.Spigot.annotations)
    kapt(Dependencies.Spigot.annotations)
    compile(Dependencies.Kotlin.stdlib)
    compileOnly(Dependencies.ProtocolLib.core) {
        exclude("com.comphenix.executors", "BukkitExecutors")
    }
    compile(Dependencies.Exposed.core)
    compile(Dependencies.Exposed.dao)
    compile(Dependencies.Exposed.jdbc)
    compile(Dependencies.Exposed.jodatime)
    compile(Dependencies.Sqlite.jdbc)
    testCompile(Dependencies.JUnit.core)
    testCompile(Dependencies.MockBukkit.core)
    compileOnly(files("libs/RxSpigot-0.1.0.jar"))
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Kotlin.classpath)
    }
}

tasks {
    withType<Jar> {
        from(configurations.getByName("compile").map { if (it.isDirectory) it else zipTree(it) })
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}