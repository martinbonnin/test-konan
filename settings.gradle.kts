pluginManagement {
    listOf(repositories, dependencyResolutionManagement.repositories).forEach {
        it.apply {
            mavenCentral()
            maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev/") }
        }
    }
}