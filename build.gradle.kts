import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.izzel.taboolib.gradle.*
import io.izzel.taboolib.gradle.Basic
import io.izzel.taboolib.gradle.Bukkit
import io.izzel.taboolib.gradle.BukkitUtil


plugins {
    java
    id("io.izzel.taboolib") version "2.0.23"
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
}

taboolib {
    env {
        install(Basic)
        install(Bukkit)
        install(BukkitUtil)
    }
    description {
        name = "ArcartXAuthView"
        desc("ArcartX Authme视图")
        contributors {
            name("17Artist")
        }
        links {
            name("https://arcartx.com/")
        }
        dependencies {
            name("ArcartX")
            name("AuthMe")
        }
    }
    version { taboolib = "6.2.3-7105e58f" }
}

repositories {
    maven {
        name = "arcartx-repo"
        url = uri("https://seventeen-artist-maven.pkg.coding.net/repository/arcartx/public/")
    }
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "codemc-repo"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }
    mavenCentral()
}

dependencies {
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")

    compileOnly("priv.seventeen.artist.arcartx:ArcartX:1.6.567.17:api")
    compileOnly("fr.xephi:authme:5.6.1-SNAPSHOT")
    compileOnly("com.google.code.gson:gson:2.10.1")

    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
