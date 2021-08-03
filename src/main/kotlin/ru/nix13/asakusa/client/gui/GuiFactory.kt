package ru.nix13.asakusa.client.gui

import cpw.mods.fml.client.IModGuiFactory
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen

class GuiFactory: IModGuiFactory {
    override fun initialize(minecraft: Minecraft) {}
    override fun mainConfigGuiClass(): Class<out GuiScreen> = AsakusaGuiConfig::class.java
    override fun runtimeGuiCategories(): MutableSet<IModGuiFactory.RuntimeOptionCategoryElement>? = null
    override fun getHandlerFor(element: IModGuiFactory.RuntimeOptionCategoryElement?): IModGuiFactory.RuntimeOptionGuiHandler? = null
}