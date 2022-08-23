import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "tcc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
    maven {
        url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    implementation("io.ktor:ktor-server-core:2.1.0")
    implementation("io.ktor:ktor-server-netty:2.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
