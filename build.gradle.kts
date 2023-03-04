plugins {
    id("java")
}

group = "io.paradaux"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("ch.qos.reload4j:reload4j:1.2.24")
    implementation("net.dv8tion:JDA:5.0.0-beta.5")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.theokanning.openai-gpt3-java:service:0.11.0")
    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("org.slf4j:slf4j-reload4j:2.0.6")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}