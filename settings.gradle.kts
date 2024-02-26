plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "betclic"

include("betclic-application")
include("betclic-infrastructure")
