/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow") version "6.0.0"
    `java-library`
    kotlin("jvm") version "1.5.10"
    `maven-publish`
    id("com.palantir.git-version") version "0.12.3"
    id("net.linguica.maven-settings") version "0.5"
    id("org.hibernate.build.maven-repo-auth") version "3.0.2"
    signing
}

allprojects {
    group = "com.valaphee"

    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "com.palantir.git-version")
    apply(plugin = "java-library")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "org.hibernate.build.maven-repo-auth")
    apply(plugin = "signing")

    val gitVersion: groovy.lang.Closure<String> by extra
    version = gitVersion()

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter:5.8.0-M1")
        compileOnly(kotlin("stdlib-jdk8"))
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "1.8"/*"15"*/
            targetCompatibility = "1.8"/*"15"*/
        }

        withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "1.8"/*"15"*/
                freeCompilerArgs = listOf("-Xinline-classes", "-Xjvm-default=compatibility", "-Xstring-concat=inline"/*"-Xstring-concat=indy-with-constants"*/)
            }
        }

        withType<Test> { useJUnitPlatform() }
    }

    signing {
        useGpgCmd()
        sign(publishing.publications)
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                artifactId = project.name

                from(components["java"])
            }
        }
    }
}
