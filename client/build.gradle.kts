tasks.jar { enabled = true }
tasks.bootJar { enabled = false }

dependencies {
    implementation(project(":common"))
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.3")
    implementation("com.google.firebase:firebase-admin:9.2.0")
}
