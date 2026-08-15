import org.kohsuke.github.GHReleaseBuilder
import org.kohsuke.github.GitHub
import java.nio.file.Files

buildscript {

    dependencies {
        classpath("org.kohsuke:github-api:1.135")
    }

    repositories {
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.minotaur)
    java
    `maven-publish`
}

val lwjglNatives = resolveLwjglNatives()

val modVersion = "${providers.gradleProperty("mod_version").get()}+${libs.versions.bta.get()}"
val modGroup: Provider<String> = providers.gradleProperty("mod_group")
val modName: Provider<String> = providers.gradleProperty("mod_name")

val javaVersion: Provider<Int> = libs.versions.java.map { it.toInt() }

base.archivesName = modName
group = modGroup.get()
version = modVersion

loom {
    val btaChannel = libs.versions.btaChannel.get()
    val btaVersion = (if (btaChannel == "nightly") "" else "v") + libs.versions.bta.get()
    customMinecraftMetadata.set("https://downloads.betterthanadventure.net/bta-client/${btaChannel}/$btaVersion/manifest.json")
    accessWidenerPath = file("src/main/resources/aether.classtweaker")
}

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/") { name = "Fabric" }
    maven("https://maven.danygames2014.net/signalum") { name = "SignalumMavenMirror1" }
//    maven("https://maven.thesignalumproject.net/infrastructure") { name = "SignalumMavenInfrastructure" }
//    maven("https://maven.thesignalumproject.net/nightly") { name = "signalumMavenNightly" }
//    maven("https://maven.thesignalumproject.net/releases") { name = "SignalumMavenReleases" }
//    maven("https://maven.thesignalumproject.net/nightly") { name = "SignalumMavenNightly" }
    maven("https://api.modrinth.com/maven") { name = "Modrinth"
        content {
            includeGroup("maven.modrinth")
        }
    }
    maven("https://jitpack.io")
    ivy("https://piston-data.mojang.com") {
        patternLayout { artifact("v1/[organisation]/[revision]/[module].jar") }
        metadataSources { artifact() }
    }
}

dependencies {
    minecraft("::${libs.versions.bta.get()}")

    // Required at compilation & runtime
    // included in builds as a runtime dependency
    implementation(libs.loader)
    implementation(libs.halplibe)
    implementation(libs.uselessNumerical)
//    implementation(libs.catalyst.core)
//    implementation(libs.catalyst.effects)

    implementation(files("libs/catalyst-core-3.0.0-beta.2+8.0.1.jar"))
    implementation(files("libs/catalyst-effects-3.0.0-beta.2+8.0.1.jar"))

    compileOnly(libs.commandly)

    // Only required at compilation
    // provides documentation, can be removed if that isn't needed
    compileOnly(libs.bundles.btaLwjgl)
    compileOnly(libs.joml)
    compileOnly(libs.joml.primitives)
    compileOnly(libs.slf4jApi)

    compileOnly(libs.jspecify)
    compileOnly(libs.errorprone)

    // Only required for development/launch at runtime, won't be part of any builds
    localRuntime(libs.modMenu) // Optional, can be removed
    runtimeClasspath(libs.clientJar)
    val lwjglVer = libs.versions.lwjgl.get()
    localRuntime(platform("org.lwjgl:lwjgl-bom:${lwjglVer}"))
    localRuntime("org.lwjgl:lwjgl::$lwjglNatives")
    localRuntime("org.lwjgl:lwjgl-glfw::$lwjglNatives")
    localRuntime("org.lwjgl:lwjgl-openal::$lwjglNatives")
    localRuntime("org.lwjgl:lwjgl-opengl::$lwjglNatives")
    localRuntime("org.lwjgl:lwjgl-stb::$lwjglNatives")
}

java {
    toolchain {
        languageVersion = javaVersion.map { JavaLanguageVersion.of(it) }
        vendor = JvmVendorSpec.ADOPTIUM
    }
    sourceCompatibility = JavaVersion.toVersion(javaVersion.get())
    targetCompatibility = JavaVersion.toVersion(javaVersion.get())
    withSourcesJar()
}

val licenseFile = run {
    val rootLicense = layout.projectDirectory.file("LICENSE")
    val parentLicense = layout.projectDirectory.file("../LICENSE")
    when {
        rootLicense.asFile.exists() -> {
            logger.lifecycle("Using LICENSE from project root: {}", rootLicense.asFile)
            rootLicense
        }
        parentLicense.asFile.exists() -> {
            logger.lifecycle("Using LICENSE from parent directory: {}", parentLicense.asFile)
            parentLicense
        }
        else -> {
            logger.warn("No LICENSE file found in project or parent directory.")
            null
        }
    }
}

tasks {
    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.get().toString()
        targetCompatibility = javaVersion.get().toString()
        if (javaVersion.get() > 8) options.release = javaVersion
    }
    named<UpdateDaemonJvm>("updateDaemonJvm") {
        languageVersion = libs.versions.gradleJava.map { JavaLanguageVersion.of(it.toInt()) }
        vendor = JvmVendorSpec.ADOPTIUM
    }
    withType<JavaExec>().configureEach { defaultCharacterEncoding = "UTF-8" }
    withType<Javadoc>().configureEach { options.encoding = "UTF-8" }
    withType<Test>().configureEach { defaultCharacterEncoding = "UTF-8" }
    withType<Jar>().configureEach {
        licenseFile?.let {
            from(it) {
                rename { original -> "${original}_${archiveBaseName.get()}" }
            }
        }
    }
    processResources {
        val resourceMap = mapOf(
            "version" to modVersion,
            "fabricloader" to libs.versions.loader.get(),
            "halplibe" to libs.versions.halplibe.get(),
            "uselessnumerical" to libs.versions.uselessNumerical.get(),
//            "catalystcore" to libs.versions.catalyst.core.get(),
//            "catalysteffects" to libs.versions.catalyst.effects.get(),
            "java" to libs.versions.java.get(),
            "modmenu" to libs.versions.modMenu.get()
        )
        // This is needed for Gradle to recognize changes
        // made to expanded files
        inputs.properties(resourceMap)

        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        with(copySpec {
            from("src/main/resources/") {
                include("fabric.mod.json")
                include("*.mixins.json")
                expand(resourceMap)
            }
        })
    }
}

configurations.configureEach {
    exclude(group = "org.lwjgl.lwjgl")
    exclude(group = "net.java.jutils")
    exclude(group = "net.java.jinput")
    exclude(group = "net.sf.jopt-simple")
    exclude(group = "net.minecraft", module = "launchwrapper")
}

publishing {
    repositories {
        maven("https://maven.thesignalumproject.net/releases") {
            name = "signalumMaven"
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = modGroup.get()
            artifactId = modName.get()
            version = modVersion
            from(components["java"])
        }
    }
}

val modrinthToken: Provider<String> = providers.gradleProperty("modrinthToken")
val githubToken: Provider<String> = providers.gradleProperty("turnipLabsGithubToken")

if (modrinthToken.isPresent) {
    modrinth {
        token = modrinthToken
        projectId = "halplibe"
        versionName = "HalpLibe $modVersion"
        versionNumber = modVersion
        versionType = "release"
        uploadFile.set(tasks.jar)
        additionalFiles = listOf(tasks.named("sourcesJar"))
        gameVersions.add("b1.7.3")
        loaders.add("bta-babric")
        changelog = Files.readString(rootProject.projectDir.toPath().resolve("CHANGELOG.md"))
    }
}

fun resolveLwjglNatives(): String { // Sourced from https://www.lwjgl.org/
    return Pair(
        System.getProperty("os.name")!!,
        System.getProperty("os.arch")!!
    ).let { (name, arch) ->
        when {
            arrayOf("Linux", "SunOS", "Unit").any { name.startsWith(it) } ->
                if (arrayOf("arm", "aarch64").any { arch.startsWith(it) })
                    "natives-linux${if (arch.contains("64") || arch.startsWith("armv8")) "-arm64" else "-arm32"}"
                else
                    "natives-linux"
            arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) } ->
                "natives-macos${if (arch.startsWith("aarch64")) "-arm64" else ""}"
            arrayOf("Windows").any { name.startsWith(it) } ->
                if (arch.contains("64"))
                    "natives-windows${if (arch.startsWith("aarch64")) "-arm64" else ""}"
                else
                    "natives-windows-x86"
            else ->
                throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
        }
    }
}

if(githubToken.isPresent){
    tasks.register("github") {
        doLast {
            val github = GitHub.connectUsingOAuth(githubToken.get())
            val repository = github.getRepository("Turnip-Labs/bta-halplibe")

            val releaseBuilder = GHReleaseBuilder(repository, version.toString())
            releaseBuilder.name("HalpLibe $version")
            releaseBuilder.body(Files.readString(rootProject.projectDir.toPath().resolve("CHANGELOG.md")))
            releaseBuilder.commitish("8.0")
            val release = releaseBuilder.create()
            release.uploadAsset(
                project.file(tasks.named("jar").get().outputs.files.singleFile),
                "application/java-archive"
            )
            release.uploadAsset(
                project.file(tasks.named("sourcesJar").get().outputs.files.singleFile),
                "application/java-archive"
            )
        }
    }

}
