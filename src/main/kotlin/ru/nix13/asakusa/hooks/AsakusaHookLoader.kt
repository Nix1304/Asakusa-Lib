package ru.nix13.asakusa.hooks

import gloomyfolken.hooklib.minecraft.HookLoader
import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer

class AsakusaHookLoader: HookLoader() {
    override fun getASMTransformerClass(): Array<String> {
        return arrayOf(PrimaryClassTransformer::class.java.name)
    }
    override fun registerHooks() {
        // registerHookContainer("ru.nix13.asakusa.hooks.Item")
    }
}