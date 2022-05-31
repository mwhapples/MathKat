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
            create("win64") {
                target = "x86_64-pc-windows-gnu"
                outputName = "mathkat64.dll"
            }
            create("win32") {
                target = "i686-pc-windows-gnu"
                outputName = "mathkat.dll"
            }
        } else {
            this += defaultTarget().apply {
                command = "cargo"
            }
        }
    }
}
