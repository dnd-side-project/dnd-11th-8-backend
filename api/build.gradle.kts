tasks.jar { enabled = true }
tasks.bootJar { enabled = true }

dependencies {
    implementation(project(":batch"))
    implementation(project(":common"))
    implementation(project(":client"))
    implementation(project(":domain:core"))
    implementation(project(":domain:redis"))
    implementation(project(":monitoring"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Jwt
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    implementation("io.jsonwebtoken:jjwt-jackson:0.12.3")
}
