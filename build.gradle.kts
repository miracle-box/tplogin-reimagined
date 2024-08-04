import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    scala
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.sagirii"
version = "1.0-SNAPSHOT"

val rootPackage = "${rootProject.group}.${rootProject.name.lowercase()}"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
        name = "spigotmc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    implementation("org.scala-lang:scala3-library_3:3.3.3")
    implementation("com.github.pureconfig:pureconfig-generic-scala3_3:0.17.7")
    compileOnly("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    // fixes build failure.
    compileOnly("org.jetbrains:annotations:24.1.0")

}

tasks.withType<ScalaCompile> {
    sourceCompatibility = "21"
    targetCompatibility = "21"

    scalaCompileOptions.additionalParameters = listOf("-no-indent")
}

tasks.withType<ProcessResources> {
    val props = mapOf("version" to project.version)
    inputs.properties(props)
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.withType<Jar> {
    manifest {
        attributes("Automatic-Module-Name" to rootPackage)
    }
}

tasks {
    withType<ShadowJar> {
        mergeServiceFiles()
        configurations = listOf(project.configurations.runtimeClasspath.get())
        relocate("scala", "shaded.${rootPackage}.scala")

        manifest {
            attributes("Automatic-Module-Name" to rootPackage)
        }
    }

    // make jar depends on shadowjar
    jar {
        dependsOn(shadowJar)
    }
}
