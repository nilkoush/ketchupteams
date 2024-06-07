plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val majorVersion = 1
val minorVersion = 0
val patchVersion = 0

group = "pro.rajce.ketchupteams"
version = "$majorVersion.$minorVersion.$patchVersion"
description = "Rajče.pro plugin for teams"

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

ext {
    set("name", "KetchupTeams")
    set("main", "$group.KetchupTeamsPlugin")
    set("version", version)
    set("description", description)
    set("author", "nilkoush")
    set("api-version", "1.20")
}

repositories {
    mavenCentral()
    maven("https://repo.purpurmc.org/snapshots")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.kryptonmc.org/releases")
    maven("https://libraries.minecraft.net/")
    maven("https://repo.nilkoush.dev/public/")
    maven("https://repo.oraxen.com/releases")
}

dependencies {
    compileOnly("org.purpurmc.purpur:purpur-api:1.20.1-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    implementation("dev.nilkoush.thelibrary:thelibrary:0.0.0-SNAPSHOT") {
        isChanging = true
    }

    compileOnly("me.neznamy:tab-api:4.0.2")
    compileOnly("me.clip:placeholderapi:2.11.5")

    compileOnly("io.th0rgal:oraxen:1.173.0")
}

tasks {
    shadowJar {
        relocate("dev.jorel.commandapi", "pro.rajce.ketchupteams.libs.commandapi")
        archiveFileName.set("${project.name}-$version.jar")
    }
    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filteringCharset = "UTF-8"
        from(sourceSets.main.get().resources.srcDirs) {
            filter(org.apache.tools.ant.filters.ReplaceTokens::class, "tokens" to mapOf(
                    "name" to project.ext.get("name"),
                    "main" to project.ext.get("main"),
                    "version" to project.ext.get("version"),
                    "description" to project.ext.get("description"),
                    "author" to project.ext.get("author"),
                    "api-version" to project.ext.get("api-version")
            ))
        }
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    build {
        dependsOn("shadowJar")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}