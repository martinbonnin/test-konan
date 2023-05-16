import okio.Buffer
import okio.buffer
import okio.source
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

buildscript {
    dependencies {
        classpath("com.squareup.okio:okio:3.3.0")
    }
}
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

            val coreFoundationDir = libraries.files.filter { it.name == "org.jetbrains.kotlin.native.platform.CoreFoundation" }.single()
            println("Computing sha1 for all files in ${coreFoundationDir}")

            val sha1 = coreFoundationDir.walk().filter {
                it.isFile
            }.sortedBy {
                it.relativeTo(coreFoundationDir).path
            }.map {
                it.source().buffer().readByteString().sha1().hex()
            }.joinToString("").let {
                Buffer().writeUtf8(it).sha1().hex()
            }

            println("sha1=$sha1")
        }
    }
}