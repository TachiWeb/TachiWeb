import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	val kotlinVersion by extra("1.3.21")

	repositories {
		mavenLocal()
		mavenCentral()
		maven { setUrl("https://plugins.gradle.org/m2/") }
	}

	dependencies {
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
	}
}

tasks.withType<Wrapper> {
	gradleVersion = "5.3"
}

allprojects {
	apply(plugin = "kotlin")

	repositories {
		mavenLocal()
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions.jvmTarget = "1.8"
		kotlinOptions.noStdlib = true
		kotlinOptions.noReflect = true
	}

	configure<JavaPluginConvention> {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
}
