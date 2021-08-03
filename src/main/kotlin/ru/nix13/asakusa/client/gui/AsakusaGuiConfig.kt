package ru.nix13.asakusa.client.gui

import cpw.mods.fml.client.config.GuiConfig
import cpw.mods.fml.client.config.IConfigElement
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.common.config.ConfigElement
import net.minecraftforge.common.config.Configuration
import ru.nix13.asakusa.AsakusaLib.Companion.modId
import ru.nix13.asakusa.config.ConfigHandler

class AsakusaGuiConfig(screen: GuiScreen): GuiConfig(
    screen,
    ConfigElement<IConfigElement<Any>>(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).childElements,
    modId,
    false,
    false,
    getAbridgedConfigPath(ConfigHandler.config.toString())
)