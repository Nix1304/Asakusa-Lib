package ru.nix13.asakusa

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.StatCollector
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import ru.nix13.asakusa.utils.UpdateChecker

@EventBusSubscriber
class Events {
    @SubscribeEvent
    fun onPlayerLogged(e: EntityJoinWorldEvent) {
        if(!e.world.isRemote) return
        if(e.entity !is EntityPlayer) return
        val info = UpdateChecker.checkForUpdates(AsakusaLib.updateUrl) ?: return
        if(info.modVersionRecommended == AsakusaLib.version) return

        val player = e.entity as EntityPlayer
        val text = ChatComponentText(
            "[${EnumChatFormatting.GREEN}Asakusa${EnumChatFormatting.RESET}] ${StatCollector.translateToLocal("ru.nix13.asakusa.update")}: ${EnumChatFormatting.AQUA}${info.modVersionRecommended}"
        )
        player.addChatComponentMessage(text)
        player.addChatComponentMessage(ChatComponentText(StatCollector.translateToLocal("ru.nix13.asakusa.update_get")))
        player.addChatComponentMessage(ForgeHooks.newChatWithLinks(info.homepage))
    }
}