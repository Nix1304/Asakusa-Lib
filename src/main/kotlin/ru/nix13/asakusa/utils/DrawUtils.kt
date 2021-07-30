package ru.nix13.asakusa.utils

import net.minecraft.client.renderer.Tessellator

object DrawUtils {
    fun drawTexture(x: Int, y: Int, u: Int, v: Int, width: Int, height: Int) {
        val startX = x.toDouble()
        val startY = y.toDouble()
        val startU = u*256.0
        val startV = v*256.0
        val endX = (x + width).toDouble()
        val endY = (y + height).toDouble()
        val endU = (u + width)*256.0
        val endV = (v + height)*256.0
        val tess = Tessellator.instance
        tess.startDrawingQuads()
        tess.addVertexWithUV(startX, startY, 0.0, startU, startV)
        tess.addVertexWithUV(startX, endY, 0.0, startU, endV)
        tess.addVertexWithUV(endX, endY, 0.0, endU, endV)
        tess.addVertexWithUV(endX, startY, 0.0, endU, startV)
        tess.draw()
    }
}