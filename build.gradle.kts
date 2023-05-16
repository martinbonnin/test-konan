import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

plugins {
    id("org.jetbrains.kotlin.multiplatform").version("1.9.20-dev-1641")
}

kotlin {
    macosArm64()
    macosX64()

    sourceSets {
        val appleMain = create("appleMain")

        appleMain.dependsOn(getByName("commonMain"))

        getByName("macosArm64Main").dependsOn(appleMain)
        getByName("macosX64Main").dependsOn(appleMain)
    }
}

tasks.all {
    if (name == "compileAppleMainKotlinMetadata") {
        doLast {
            this as KotlinNativeCompile
            println(libraries.joinToString("") { it.absolutePath + "\n" })
        }
    }
}