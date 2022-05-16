pluginManagement.repositories {
    mavenLocal()
    gradlePluginPortal()
}

rootProject.name = "MathKat"

include("rust-library")
include("jvm-api")

