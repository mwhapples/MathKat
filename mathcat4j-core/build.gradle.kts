/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Copyright 2022 Michael Whapples
 */
plugins {
    kotlin("jvm") version "1.7.0"
    id("org.jetbrains.dokka") version "1.7.0"
    id("fr.stardustenterprises.rust.importer") version "3.2.1"
    `maven-publish`
    signing
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.java.dev.jna", "jna", "5.11.0")
    rust(project(":rust-library"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    systemProperty("onl.mdw.mathcat4j.testRulesDir", File("$rootDir/MathCAT/Rules").absolutePath)
    systemProperty("onl.mdw.mathcat4j.testVersion", "${project.version}")
}

java {
    // withJavadocJar()
    withSourcesJar()
}

val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("html-doc")
}

val rulesZip by tasks.register<Zip>("rulesZip") {
    from(layout.projectDirectory.dir("../MathCAT/Rules")) {
        into("MathCAT/Rules")
    }
    archiveClassifier.set("rules")
}

rustImport {
    baseDir.set("/META-INF/native")
    layout.set("flat")
}

publishing {
    publications {
        register<MavenPublication>("Maven") {
            from(components["java"])
            artifact(rulesZip)
            artifact(dokkaJavadocJar)
            artifact(dokkaHtmlJar)
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("MathKat")
                description.set("Java bindings for MathCAT")
                url.set("https://github.com/mwhapples/MathKat")
                licenses {
                    license {
                        name.set("Mozilla Public License 2.0")
                        url.set("https://www.mozilla.org/MPL/2.0/")
                    }
                }
                developers {
                    developer {
                        id.set("mwhapples")
                        name.set("Michael Whapples")
                        email.set("mwhapples@aim.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/mwhapples/MathKat.git")
                    developerConnection.set("scm:git:ssh://github.com:mwhapples/MathKat.git")
                    url.set("https://github.com/mwhapples/MathKat")
                }
            }
        }
    }
}
signing {
    sign(publishing.publications["Maven"])
}
