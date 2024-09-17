plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "blooming"
include("api")
include("batch")
include("common")
include("domain:core")
include("domain:redis")
include("client")
