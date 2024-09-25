tasks.jar { enabled = true }
tasks.bootJar { enabled = false }

dependencies {
    implementation(project(":common"))
    implementation(project(":client"))
    implementation(project(":domain:core"))
    implementation(project(":domain:redis"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.boot:spring-boot-starter-batch")
    testImplementation("org.springframework.batch:spring-batch-test")
}
