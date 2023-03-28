plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":entity"))

    testImplementation(kotlin("test-junit5"))
    testImplementation("io.kotest:kotest-assertions-core:5.1.0")
    testImplementation(Dependencies.mockk)
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.8.0")
    testImplementation(Dependencies.coroutineTest)

    //testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation(Dependencies.coroutineAndroid)
}