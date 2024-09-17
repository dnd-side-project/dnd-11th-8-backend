tasks.jar { enabled = true }
tasks.bootJar { enabled = false }

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}
