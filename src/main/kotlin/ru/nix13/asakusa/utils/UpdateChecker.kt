package ru.nix13.asakusa.utils

import com.google.gson.JsonParser
import cpw.mods.fml.common.Loader
import ru.nix13.asakusa.AsakusaLib.Companion.logger
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import kotlin.jvm.Throws

data class UpdateInfo(val homepage: String, val modVersionRecommended: String, val modVersionLatest: String, val changelogRecommended: String, val changelogLatest: String)

object UpdateChecker {
    fun checkForUpdates(url: String): UpdateInfo? {
        var stream: InputStream? = null
        try {
            stream = URL(url).openStream()
        } catch (e: FileNotFoundException) {
            logger.fatal("Can't load update URL. URL: $url")
        }
        if(stream == null) return null
        try {
            val reader = InputStreamReader(stream)
            val jsonTree = JsonParser().parse(reader)
            if(jsonTree.isJsonObject) {
                val homepage = jsonTree.asJsonObject.get("homepage").asString
                val promos = jsonTree.asJsonObject.get("promos")
                if(promos.isJsonObject) {
                    val mcVersion = Loader.MC_VERSION
                    val modVersionRecommended = promos.asJsonObject.get("$mcVersion-recommended").asString
                    val modVersionLatest = promos.asJsonObject.get("$mcVersion-latest").asString
                    val changelogRecommended = jsonTree.asJsonObject.get(mcVersion).asJsonObject.get(modVersionRecommended).asString
                    val changelogLatest = jsonTree.asJsonObject.get(mcVersion).asJsonObject.get(modVersionLatest).asString
                    UpdateInfo(homepage, modVersionRecommended, modVersionLatest, changelogRecommended, changelogLatest)
                }
            }
        } catch (e: Exception) {
            logger.fatal("Can't check updates. Error: ${e.message}")
        }
        return null
    }
}