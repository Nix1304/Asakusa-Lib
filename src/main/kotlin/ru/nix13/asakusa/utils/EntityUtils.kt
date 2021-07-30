package ru.nix13.asakusa.utils

import net.minecraft.entity.Entity
import net.minecraft.util.MathHelper
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.Vec3
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

object EntityUtils {
    fun raytraceFromEntity(world: World, player: Entity, range: Double): MovingObjectPosition? {
        val f: Float = player.rotationPitch
        val f1: Float = player.rotationYaw
        val d0: Double = player.posX
        val d1: Double = player.posY + player.eyeHeight.toDouble()
        val d2: Double = player.posZ
        val from = Vec3.createVectorHelper(d0, d1, d2)
        val f2: Float = MathHelper.cos(-f1 * 0.017453292f - Math.PI.toFloat())
        val f3: Float = MathHelper.sin(-f1 * 0.017453292f - Math.PI.toFloat())
        val f4: Float = -MathHelper.cos(-f * 0.017453292f)
        val f5: Float = MathHelper.sin(-f * 0.017453292f)
        val f6 = f3 * f4
        val f7 = f2 * f4
        val to = from.addVector(f6.toDouble() * range, f5.toDouble() * range, f7.toDouble() * range)
        return world.rayTraceBlocks(from, to, false)
    }

    fun raytraceFromEntityDirection(world: World, player: Entity, range: Double): ForgeDirection {
        val mop = raytraceFromEntity(world, player, range) ?: return ForgeDirection.UNKNOWN
        return ForgeDirection.getOrientation(mop.sideHit)
    }
}