/*
 * Copyright (c) 2021, Valaphee.
 * All rights reserved.
 */

rootProject.name = "foundry"
file(".").walk().maxDepth(1).filter { it.isDirectory && it.name != "foundry" && File(it, "build.gradle.kts").exists() }.forEach { include(it.name) }
