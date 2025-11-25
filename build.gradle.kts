@file:Suppress("UnstableApiUsage")
import com.smushytaco.lwjgl_gradle.Preset
plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.lwjgl)
    java
}
val modVersion = providers.gradleProperty("mod_version")
val modGroup = providers.gradleProperty("mod_group")
val modName = providers.gradleProperty("mod_name")

val javaVersion = libs.versions.java.map { it.toInt() }

base.archivesName = modName
group = modGroup.get()
version = modVersion.get()
loom {
    noIntermediateMappings()
    customMinecraftMetadata.set("https://downloads.betterthanadventure.net/bta-client/${libs.versions.btaChannel.get()}/v${libs.versions.bta.get()}/manifest.json")
}
repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.fabricmc.net/") { name = "Fabric" }
    maven("https://maven.thesignalumproject.net/infrastructure") { name = "SignalumMavenInfrastructure" }
    maven("https://maven.thesignalumproject.net/releases") { name = "SignalumMavenReleases" }
    ivy("https://github.com/Better-than-Adventure") {
        patternLayout { artifact("[organisation]/releases/download/v[revision]/[module].jar") }
        metadataSources { artifact() }
    }
    ivy("https://downloads.betterthanadventure.net/bta-client/${libs.versions.btaChannel.get()}/") {
        patternLayout { artifact("/v[revision]/client.jar") }
        metadataSources { artifact() }
    }
    ivy("https://downloads.betterthanadventure.net/bta-server/${libs.versions.btaChannel.get()}/") {
        patternLayout { artifact("/v[revision]/server.jar") }
        metadataSources { artifact() }
    }
    ivy("https://piston-data.mojang.com") {
        patternLayout { artifact("v1/[organisation]/[revision]/[module].jar") }
        metadataSources { artifact() }
    }
    ivy("https://github.com/") {
        patternLayout { artifact("v1/[organisation]/[revision]/[module].jar") }
        metadataSources { artifact() }
    }
    ivy ("https://github.com/"){
        patternLayout { artifact("[organization]/[module]/releases/download/[revision]/[module]-[revision].jar")}
        metadataSources { artifact() }
    }
}
lwjgl {
    version = libs.versions.lwjgl
    implementation(Preset.MINIMAL_OPENGL)
}
dependencies {
    minecraft("::${libs.versions.bta.get()}")
    mappings(loom.layered {})

    // https://piston-data.mojang.com/v1/objects/43db9b498cb67058d2e12d394e6507722e71bb45/client.jar
    modImplementation("objects:client:43db9b498cb67058d2e12d394e6507722e71bb45")
    modImplementation(libs.loader)
    modImplementation(libs.halplibe)
    modImplementation(libs.modMenu)
    modImplementation(libs.legacyLwjgl)

    modImplementation(libs.dragonfly)
    modImplementation(libs.catalyst.core)
    modImplementation(libs.catalyst.effects)
    modImplementation(libs.uselessNumerical.get().let { "${it.group}:${it.name}:${it.version}-${libs.versions.bta.get()}" })

    modCompileOnly(libs.btwaila)
    modCompileOnly(libs.commandly)

    implementation(libs.slf4jApi)
    implementation(libs.guava)
    implementation(libs.log4j.slf4j2.impl)
    implementation(libs.log4j.core)
    implementation(libs.log4j.api)
    implementation(libs.log4j.api12)
    implementation(libs.gson)

    implementation(libs.commonsLang3)
    include(libs.commonsLang3)
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
        val stringModVersion = modVersion.get()
        val stringLoaderVersion = libs.versions.loader.get()
        val stringJavaVersion = libs.versions.java.get()
        val stringDragonflyVersion = libs.versions.dragonfly.get()
        val stringHalplibeVersion = libs.versions.halplibe.get()
        val stringModMenuVersion = libs.versions.modMenu.get()

        val stringCatalystCoreVersion = libs.versions.catalyst.core.get()
        val stringCatalystEffectsVersion = libs.versions.catalyst.effects.get()
        val stringUselessNumericalVersion = libs.versions.uselessNumerical.get()

        inputs.property("modVersion", stringModVersion)
        inputs.property("loaderVersion", stringLoaderVersion)
        inputs.property("javaVersion", stringJavaVersion)
        inputs.property("dragonflyVersion", stringDragonflyVersion)
        inputs.property("halplibeVersion", stringHalplibeVersion)
        inputs.property("modMenuVersion", stringModMenuVersion)

        inputs.property("catalystCoreVersion", stringCatalystCoreVersion)
        inputs.property("catalystEffectsVersion", stringCatalystEffectsVersion)
        inputs.property("uselessNumericalVersion", stringUselessNumericalVersion)

        filesMatching("fabric.mod.json") {
            expand(
                mapOf(
                    "version" to stringModVersion,
                    "fabricloader" to stringLoaderVersion,
                    "dragonfly" to stringDragonflyVersion,
                    "halplibe" to stringHalplibeVersion,
                    "java" to stringJavaVersion,
                    "modmenu" to stringModMenuVersion,

                    "catalystcore" to stringCatalystCoreVersion,
                    "catalysteffects" to stringCatalystEffectsVersion,
                    "uselessnumerical" to stringUselessNumericalVersion
                )
            )
        }
        filesMatching("**/*.mixins.json") { expand(mapOf("java" to stringJavaVersion)) }
    }
}
// Removes LWJGL2 dependencies
configurations.configureEach { exclude(group = "org.lwjgl.lwjgl") }
