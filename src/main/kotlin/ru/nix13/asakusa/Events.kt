package ru.nix13.asakusa

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.StatCollector
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import ru.nix13.asakusa.config.ConfigHandler
import ru.nix13.asakusa.utils.UpdateChecker

@EventBusSubscriber
class Events {
    @SubscribeEvent
    fun onPlayerLogged(e: EntityJoinWorldEvent) {
        if(!ConfigHandler.updateCheck) return
        if(!e.world.isRemote || e.entity !is EntityPlayer) return
        val info = UpdateChecker.checkForUpdates(AsakusaLib.updateUrl) ?: return
        println(ConfigHandler.useLatestBranch)
        var version = ""
        if(ConfigHandler.useLatestBranch && info.modVersionLatest != AsakusaLib.version) version = info.modVersionLatest
        if(!ConfigHandler.useLatestBranch && info.modVersionRecommended != AsakusaLib.version) version = info.modVersionRecommended


        val player = e.entity as EntityPlayer
        val text = ChatComponentText(
            "[${EnumChatFormatting.GREEN}Asakusa${EnumChatFormatting.RESET}] ${StatCollector.translateToLocal("ru.nix13.asakusa.update")}: ${EnumChatFormatting.AQUA}$version"
        )
        player.addChatComponentMessage(text)
        player.addChatComponentMessage(ChatComponentText(StatCollector.translateToLocal("ru.nix13.asakusa.update_get")))
        player.addChatComponentMessage(ForgeHooks.newChatWithLinks(info.homepage))
    }
}