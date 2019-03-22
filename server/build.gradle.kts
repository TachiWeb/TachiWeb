import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    java
    id("org.beryx.jlink") version "2.6.6"
    id("org.javamodularity.moduleplugin") version "1.4.1"
}

// Required or jlink will break randomly
val compileKotlin: KotlinCompile by tasks
val compileJava: JavaCompile by tasks
compileJava.destinationDir = compileKotlin.destinationDir

val kotlinVersion: String by rootProject.extra

dependencies {
    // we just list all modular artifacts here first,
    // so that automatic module artifacts of stdlib from transitive dependencies are ignored
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion:modular")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion:modular")
    api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion:modular")

    testCompile("junit", "junit", "4.12")
}

val mainClass = "ax.nd.tachiweb.server.Main"
val moduleName: String by project
application {
    mainClassName = "$moduleName/$mainClass"
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to "modular-hello",
                "Main-Class" to "org.beryx.modular.hello.Hello"
            )
        )
    }
}

jlink {
    addOptions("--compress", "2",
        "--no-header-files",
        "--no-man-pages")
    mergedModule {
        requires("java.xml")
    }
    launcher {
        name = "tachiweb"
    }
}