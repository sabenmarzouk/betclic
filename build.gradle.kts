plugins {
    kotlin("jvm") version "1.9.21"
}


repositories {
    mavenCentral()
}
group = "com.walid"
version = "0.0.1-SNAPSHOT"
dependencies {
    implementation(project(":betclic-application"))
    implementation(project(":betclic-infrastructure"))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

kotlin {
    jvmToolchain(21)
}

subprojects {
    plugins.apply("java")

    tasks.test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}