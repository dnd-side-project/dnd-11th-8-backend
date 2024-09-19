tasks.jar { enabled = true }
tasks.bootJar { enabled = false }

dependencies {
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.github.loki4j:loki-logback-appender:1.5.1")
}
