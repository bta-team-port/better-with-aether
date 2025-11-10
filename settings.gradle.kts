val modName = providers.gradleProperty("mod_name")
rootProject.name = modName.get()
pluginManagement {
	repositories {
		gradlePluginPortal()
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		maven("https://jitpack.io") { name = "Jitpack" }
		maven("https://maven.glass-launcher.net/babric") { name = "Babric" }
		maven("https://maven.thesignalumproject.net/infrastructure") { name = "SignalumMavenInfrastructure" }
	}
	val loomVersion = providers.gradleProperty("loom_version")
	plugins { id("fabric-loom").version(loomVersion.get()) }
}
