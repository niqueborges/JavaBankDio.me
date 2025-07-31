plugins {
    id("java")
    // Adicione o plugin de aplicação
    id("application")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

group = "br.com.dio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")


    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Especifique a classe principal da sua aplicação
application {
    mainClass.set("br.com.dio.Main")
}

// Configura a task 'run' para aceitar entrada do console
tasks.named<org.gradle.api.tasks.JavaExec>("run") {
    standardInput = System.`in`
}