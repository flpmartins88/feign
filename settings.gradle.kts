rootProject.name = "feign"

include("requester", "api")

pluginManagement {
    repositories {
        jcenter()
        gradlePluginPortal()
    }
}