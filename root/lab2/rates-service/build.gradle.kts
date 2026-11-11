plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "ru.cofffeen"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.kafka:spring-kafka")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

springBoot {
    mainClass.set("ru.cofffeen.RatesApplication")
}

tasks.withType<JavaCompile> {
    options.release.set(21)
}