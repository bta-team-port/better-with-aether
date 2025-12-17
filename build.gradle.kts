import com.smushytaco.lwjgl_gradle.Preset
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers

plugins {
    alias(libs.plugins.loom)
    alias(libs.plugins.lwjgl)
    alias(libs.plugins.ideaExt)
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
val clientJarForExtract by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}
val extractPaulscodeJar = tasks.register<Jar>("extractPaulscodeJar") {
    description = "Extracts the needed jar."
    group = JavaBasePlugin.BUILD_DEPENDENTS_TASK_NAME
    archiveBaseName.set("paulscode")
    archiveVersion.set("")
    destinationDirectory.set(layout.buildDirectory.dir("generated-libs"))

    from(
        {
            val clientJarFile = clientJarForExtract.singleFile
            zipTree(clientJarFile)
        }
    ) {
        include("paulscode/**")
    }
}
idea {
    project {
        settings {
            taskTriggers {
                afterSync(extractPaulscodeJar)
            }
        }
    }
}
dependencies {
    minecraft("::${libs.versions.bta.get()}")

    compileOnly(libs.btwaila)
    compileOnly(libs.commandly)

    compileOnly(
        files(extractPaulscodeJar.flatMap { it.archiveFile })
            .builtBy(extractPaulscodeJar)
    )
    clientJarForExtract(libs.clientJar)
    runtimeOnly(libs.clientJar)
    implementation(libs.loader)
    implementation(libs.halplibe)
    implementation(libs.modMenu)
    implementation(libs.legacyLwjgl)

    implementation(libs.dragonfly)
    implementation(libs.catalyst.core)
    implementation(libs.catalyst.effects)
    implementation(libs.uselessNumerical.get().let { "${it.group}:${it.name}:${it.version}-${libs.versions.bta.get()}" })

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
    named("clean") {
        finalizedBy(extractPaulscodeJar)
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
            "version" to modVersion.get(),
            "fabricloader" to libs.versions.loader.get(),
            "dragonfly" to libs.versions.dragonfly.get(),
            "halplibe" to libs.versions.halplibe.get(),
            "java" to libs.versions.java.get(),
            "modmenu" to libs.versions.modMenu.get(),
            "catalystcore" to libs.versions.catalyst.core.get(),
            "catalysteffects" to libs.versions.catalyst.effects.get(),
            "uselessnumerical" to libs.versions.uselessNumerical.get()
        )
        inputs.properties(resourceMap)
        filesMatching("fabric.mod.json") { expand(resourceMap) }
        filesMatching("**/*.mixins.json") { expand(resourceMap.filterKeys { it == "java" }) }
    }
}
// Removes LWJGL2 dependencies
configurations.configureEach { exclude(group = "org.lwjgl.lwjgl") }
