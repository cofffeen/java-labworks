plugins {
    application
    id("org.springframework.boot")
}

application {
    mainClass.set("ru.cofffeen.presentation.Application")
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.4.0"))
    implementation(project(":application"))
    implementation(project(":data-access"))
    implementation(project(":domain"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    implementation("org.springframework:spring-messaging")
    implementation("org.springframework.kafka:spring-kafka")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.hamcrest:hamcrest")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = true
    mainClass.set("ru.cofffeen.Application")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

springBoot {
    mainClass.set("ru.cofffeen.Application")
}