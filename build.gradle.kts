plugins {
    id("java")
}

group = "io.paradaux"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.5")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.theokanning.openai-gpt3-java:service:0.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}