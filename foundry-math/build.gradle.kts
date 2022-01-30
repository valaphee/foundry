/*
 * Copyright (c) 2021-2022, Valaphee.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

dependencies { implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5") }

publishing {
    publications {
        create<MavenPublication>("maven") {
            pom.apply {
                name.set("Foundry Math")
                description.set("A library of lightweight, self-contained mathematics addressing the most common problems not available in the Java/Kotlin programming language.")
                url.set("https://valaphee.com")
                scm {
                    connection.set("https://github.com/valaphee/foundry.git")
                    developerConnection.set("https://github.com/valaphee/foundry.git")
                    url.set("https://github.com/valaphee/foundry")
                }
                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("https://raw.githubusercontent.com/valaphee/foundry/master/LICENSE.txt")
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
