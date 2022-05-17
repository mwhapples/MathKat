plugins {
    kotlin("jvm") version "1.6.21"
    id("fr.stardustenterprises.rust.importer") version "3.2.1"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("fr.stardustenterprises", "yanl", "0.8.0")
    rust(project(":rust-library"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    systemProperty("onl.mdw.mathkat.rulesDir", File("$rootDir/MathCAT/Rules/").absolutePath)
}
rustImport {
    baseDir.set("/META-INF/native")
    layout.set("hierarchical")
}
