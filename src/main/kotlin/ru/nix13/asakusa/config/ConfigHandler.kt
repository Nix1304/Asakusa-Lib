package ru.nix13.asakusa.config

import cpw.mods.fml.client.event.ConfigChangedEvent
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.common.config.Configuration
import ru.nix13.asakusa.AsakusaLib.Companion.modId
import ru.nix13.asakusa.EventBusSubscriber
import java.io.File

@EventBusSubscriber
object ConfigHandler {
    lateinit var config: Configuration

    var updateCheck = true
    var useLatestBranch = false

    fun init(configDir: String) {
        val path = File("$configDir/$modId.cfg")
        config = Configuration(path)
        loadConfig()
    }

    private fun loadConfig() {
        updateCheck = config.getBoolean("Check for update", Configuration.CATEGORY_GENERAL, true, "Allow to check updates")
        useLatestBranch = config.getBoolean("Use latest branch", Configuration.CATEGORY_GENERAL, false, "Check for latest version of mod")

       if(config.hasChanged())
           config.save()
    }

    @SubscribeEvent
    fun onConfigChangedEvent(e: ConfigChangedEvent.OnConfigChangedEvent) {
        if(e.modID.equals(modId, true)) loadConfig()
    }
}