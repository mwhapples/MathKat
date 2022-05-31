/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */

plugins {
    id("fr.stardustenterprises.rust.wrapper") version "3.2.1"
}

val buildAllPlatforms: String? by project

rust {
    release.set(true)
    cargoInstallTargets.set(true)
    targets {
        if (buildAllPlatforms != null) {
            create("win-x64") {
                target = "x86_64-pc-windows-gnu"
                outputName = "mathkat64.dll"
            }
            create("win-x86") {
                target = "i686-pc-windows-gnu"
                outputName = "mathkat.dll"
            }
            create("linux-x64") {
                target = "x86_64-unknown-linux-gnu"
                outputName = "libmathkat64.so"
            }
            //create("linux-aarch64") {
            //    target = "aarch64-unknown-linux-gnu"
            //    outputName = "libmathkat64.so"
            //    command = "cross"
            //}
            create("macOS-x64") {
                target = "x86_64-apple-darwin"
                outputName = "libmathkat64.dylib"
                env += "CC" to "x86_64-apple-darwin20.4-clang"
                env += "CXX" to "x86_64-apple-darwin20.4-clang++"
            }
            create("macOS-aarch64") {
                target = "aarch64-apple-darwin"
                outputName = "libmathkat64.dylib"
                env += "CC" to "aarch64-apple-darwin20.4-clang"
                env += "CXX" to "aarch64-apple-darwin20.4-clang++"
            }
        } else {
            this += defaultTarget()
        }
    }
}
