/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.palantir.git-version") version "0.12.3"
    `java-library`
    kotlin("jvm") version "1.5.10"
    `maven-publish`
    signing
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "com.palantir.git-version")
    apply(plugin = "java-library")
    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    group = "com.valaphee"
    val gitVersion: groovy.lang.Closure<String> by extra
    version = gitVersion()

    dependencies {
        testImplementation("org.junit.jupiter:junit-jupiter:5.8.0-M1")
        compileOnly(kotlin("stdlib-jdk8"))
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "16"
            targetCompatibility = "16"
        }

        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "16"
                freeCompilerArgs = listOf("-Xlambdas=indy", "-Xsam-conversions=indy")
            }
        }

        withType<Test> { useJUnitPlatform() }
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    signing {
        useGpgCmd()
        sign(publishing.publications)
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                pom.apply {
                    name.set(project.name)
                    description.set("Toolkit")
                    url.set("https://github.com/Valaphee/foundry")
                    scm {
                        connection.set("https://github.com/Valaphee/foundry.git")
                        developerConnection.set("https://github.com/Valaphee/foundry.git")
                        url.set("https://github.com/Valaphee/foundry")
                    }
                    licenses {
                        license {
                            name.set("All rights reserved")
                            url.set("https://raw.githubusercontent.com/Valaphee/foundry/master/LICENSE.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("valaphee")
                            name.set("Valaphee")
                            email.set("iam@valaphee.com")
                            roles.add("owner")
                        }
                    }
                }

                from(components["java"])
            }
        }
    }
}
