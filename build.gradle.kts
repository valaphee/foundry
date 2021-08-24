/*
 * MIT License
 *
 * Copyright (c) 2021, Valaphee.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.palantir.git-version") version "0.12.3"
    `java-library`
    kotlin("jvm") version "1.5.30-M1"
    id("net.linguica.maven-settings") version "0.5"
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
    val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
    val details = versionDetails()
    version = "${details.lastTag}.${details.commitDistance}${if (!details.isCleanTag) ".dirty" else ""}${if (details.branchName != "master") "-${details.branchName.split('/').last()}" else ""}"

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
}
