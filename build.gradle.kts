group = "io.github.flpmartins88"
version = "1.0.0"

plugins {
    id("org.springframework.boot")        version "2.2.2.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.8.RELEASE" apply false
    kotlin("jvm")                     version "1.3.61"        apply false
    kotlin("plugin.spring")           version "1.3.61"        apply false
    kotlin("plugin.jpa")              version "1.3.61"        apply false
}

allprojects {
    group = "io.github.flpmartins88"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

