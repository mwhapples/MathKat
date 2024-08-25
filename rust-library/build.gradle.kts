/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */

plugins {
    id("fr.stardustenterprises.rust.wrapper") version "3.2.5"
}

val buildAllPlatforms: String? by project

rust {
    release.set(true)
    cargoInstallTargets.set(true)
    targets {
        if (buildAllPlatforms != null) {
            create("win-x64") {
                target = "x86_64-pc-windows-gnu"
                outputName = "mathcat4j-win32-x86-64.dll"
            }
            create("win-x86") {
                target = "i686-pc-windows-gnu"
                outputName = "mathcat4j-win32-x86.dll"
            }
            create("linux-x64") {
                target = "x86_64-unknown-linux-gnu"
                outputName = "libmathcat4j-linux-x86-64.so"
            }
            create("linux-aarch64") {
                target = "aarch64-unknown-linux-gnu"
                outputName = "libmathcat4j-linux-aarch64.so"
            }
            create("linux-armhf") {
                target = "arm-unknown-linux-gnueabihf"
                outputName = "libmathcat4j-linux-arm.so"
            }
            create("macOS-x64") {
                target = "x86_64-apple-darwin"
                outputName = "libmathcat4j-darwin-x86-64.dylib"
                env += "CC" to "o64-clang"
                env += "CXX" to "o64-clang++"
            }
            create("macOS-aarch64") {
                target = "aarch64-apple-darwin"
                outputName = "libmathcat4j-darwin-aarch64.dylib"
                env += "CC" to "oa64-clang"
                env += "CXX" to "oa64-clang++"
            }
        } else {
            this += defaultTarget()
        }
    }
}
