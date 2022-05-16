plugins {
    id("fr.stardustenterprises.rust.wrapper") version "3.2.1"
}

rust {
    release.set(true)
    cargoInstallTargets.set(true)
    targets {
        this += defaultTarget().apply {
            command = "cargo"
        }
    }
}