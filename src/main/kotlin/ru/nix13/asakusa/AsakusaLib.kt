package ru.nix13.asakusa

import cpw.mods.fml.common.*
import cpw.mods.fml.common.event.FMLConstructionEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.common.MinecraftForge
import org.apache.logging.log4j.Logger

@Mod(modid = AsakusaLib.modId, name = AsakusaLib.name, version = AsakusaLib.version)
class AsakusaLib {
    companion object {
        const val modId = "asakusa-lib"
        const val name = "AsakusaLib"
        const val version = "1.0.1"
        const val updateUrl = "https://nix13.pw/mods/asakusa_lib.json"
        lateinit var logger: Logger
    }

    @Mod.EventHandler
    fun constructor(e: FMLConstructionEvent) {
        val mods = Loader.instance().modList
        val mcl = Loader.instance().modClassLoader

        mods.filterIsInstance<FMLModContainer>().forEach { mod ->
            FMLLog.getLogger().debug("Attempting to inject @EventBusSubscriber classes into the eventbus for {}", mod.modId)
            val modData = e.asmHarvestedData.getAnnotationsFor(mod)
            val targets = modData.get(EventBusSubscriber::class.java.name)

            targets.forEach { target ->
                FMLLog.getLogger().debug("Registering @EventBusSubscriber for {} for mod {}", target.className, mod.modId)
                val subscriptionTarget = Class.forName(target.className, false, mcl)

                var instance: Any? = null
                try {
                    instance = subscriptionTarget.declaredFields.first { it.name == "INSTANCE" }?.get(null)
                } catch (_: Throwable) {}
                if(instance == null) instance = subscriptionTarget.newInstance()

                MinecraftForge.EVENT_BUS.register(instance)
                FMLCommonHandler.instance().bus().register(instance)
                FMLLog.getLogger().debug("Injected @EventBusSubscriber class {}", target.className)
            }
        }
    }

    @Mod.EventHandler
    fun preInit(e: FMLPreInitializationEvent) {
        logger = e.modLog
        modMetadata(e.modMetadata)
    }

    private fun modMetadata(meta: ModMetadata) {
        meta.autogenerated = false
        meta.modId = modId
        meta.version = version
        meta.name = name
        meta.url = "https://www.curseforge.com/minecraft/mc-mods/asakusalib"
        val authorList = meta.authorList
        authorList.add("Nix13")
    }
}