import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.31"
}

group = "com.orange.ccmd.dojo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("io.kotlintest:kotlintest-assertions:3.3.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}