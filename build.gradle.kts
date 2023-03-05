plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
    application
}

group = "io.paradaux"
version = "1.1.0"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("ch.qos.reload4j:reload4j:1.2.24")
    implementation("net.dv8tion:JDA:5.0.0-beta.5")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.theokanning.openai-gpt3-java:service:0.11.0")
    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("org.slf4j:slf4j-reload4j:2.0.6")
    implementation("org.spongepowered:configurate-hocon:4.1.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

application {
    mainClass.set("io.paradaux.Main")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}