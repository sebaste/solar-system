plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.3")
    implementation("com.google.guava:guava:30.1-jre")
    implementation("javax.validation:validation-api:2.0.1.Final")
    implementation("org.mockito:mockito-all:1.8.5")
    implementation("org.projectlombok:lombok:1.18.18")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
}

application {
    mainClass.set("solarsystem.App")
}

tasks.test {
    useJUnitPlatform()
}
