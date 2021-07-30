package ru.nix13.asakusa.utils

import com.google.gson.JsonParser
import cpw.mods.fml.common.Loader
import java.io.InputStreamReader
import java.net.URL

data class UpdateInfo(val homepage: String, val modVersionRecommended: String, val modVersionLatest: String, val changelogRecommended: String, val changelogLatest: String)

object UpdateChecker {
    fun checkForUpdates(url: String): UpdateInfo? {
        val reader = InputStreamReader(URL(url).openStream())
        val jsonTree = JsonParser().parse(reader)
        if(jsonTree.isJsonObject) {
            val homepage = jsonTree.asJsonObject.get("homepage").asString
            val promos = jsonTree.asJsonObject.get("promos")
            if(promos.isJsonObject) {
                return try {
                    val mcVersion = Loader.MC_VERSION
                    val modVersionRecommended = promos.asJsonObject.get("$mcVersion-recommended").asString
                    val modVersionLatest = promos.asJsonObject.get("$mcVersion-latest").asString
                    val changelogRecommended = jsonTree.asJsonObject.get(mcVersion).asJsonObject.get(modVersionRecommended).asString
                    val changelogLatest = jsonTree.asJsonObject.get(mcVersion).asJsonObject.get(modVersionLatest).asString
                    UpdateInfo(homepage, modVersionRecommended, modVersionLatest, changelogRecommended, changelogLatest)
                } catch(e: Exception) {
                    null
                }
            }
        }
        return null
    }
}