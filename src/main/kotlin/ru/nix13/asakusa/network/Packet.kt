package ru.nix13.asakusa.network

import cpw.mods.fml.relauncher.Side

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class Packet(vararg val side: Side = [Side.CLIENT, Side.SERVER])