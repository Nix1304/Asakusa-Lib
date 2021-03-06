package ru.nix13.asakusa.utils

import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

object WorldUtils {
    fun getBlock(world: World, pos: Coordinates): Block {
        return world.getBlock(pos.x, pos.y, pos.z)
    }

    fun getBlockMeta(world: World, pos: Coordinates): Int {
        val block = world.getBlock(pos.x, pos.y, pos.z)
        return block.getDamageValue(world, pos.x, pos.y, pos.z)
    }

    fun digBlock(world: World, x: Int, y: Int, z: Int) {
        val block = world.getBlock(x, y, z)
        block.dropBlockAsItem(world, x, y, z, 0, 0)
        world.setBlockToAir(x, y, z)
    }

    fun digBlock(world: World, x: Int, y: Int, z: Int, fortune: Int) {
        val block = world.getBlock(x, y, z)
        block.dropBlockAsItem(world, x, y, z, 0, fortune)
        world.setBlockToAir(x, y, z)
    }

    fun digBlock(world: World, pos: Coordinates) {
        val block = world.getBlock(pos.x, pos.y, pos.z)
        block.dropBlockAsItem(world, pos.x, pos.y, pos.z, 0, 0)
        world.setBlockToAir(pos.x, pos.y, pos.z)
    }

    fun digBlock(world: World, pos: Coordinates, fortune: Int) {
        val block = world.getBlock(pos.x, pos.y, pos.z)
        block.dropBlockAsItem(world, pos.x, pos.y, pos.z, 0, fortune)
        world.setBlockToAir(pos.x, pos.y, pos.z)
    }

    fun digHole(range: Int, player: EntityPlayer, pos: Coordinates, world: World, fortune: Int): Int {
        val size = range / 2
        var diggedBlocks = 0
        for(i in -size..size) {
            for(j in -size..size) {
                when(EntityUtils.raytraceFromEntityDirection(world, player, 6.0)) {
                    ForgeDirection.SOUTH, ForgeDirection.NORTH -> {
                        val coordinates = Coordinates(pos.x + i, pos.y + j, pos.z)
                        val block = getBlock(world, coordinates)
                        val meta = getBlockMeta(world, coordinates)

                        if(block.isToolEffective("pickaxe", meta) || block.isToolEffective("shovel", meta)) {
                            digBlock(world, coordinates, fortune)
                            diggedBlocks++
                        }
                    }
                    ForgeDirection.EAST, ForgeDirection.WEST -> {
                        val coordinates = Coordinates(pos.x, pos.y + i, pos.z + j)
                        val block = getBlock(world, coordinates)
                        val meta = getBlockMeta(world, coordinates)

                        if(block.isToolEffective("pickaxe", meta) || block.isToolEffective("shovel", meta)) {
                            digBlock(world, coordinates, fortune)
                            diggedBlocks++
                        }
                    }
                    ForgeDirection.DOWN, ForgeDirection.UP -> {
                        val coordinates = Coordinates(pos.x + i, pos.y, pos.z + j)
                        val block = getBlock(world, coordinates)
                        val meta = getBlockMeta(world, coordinates)

                        if(block.isToolEffective("pickaxe", meta) || block.isToolEffective("shovel", meta)) {
                            digBlock(world, coordinates, fortune)
                            diggedBlocks++
                        }
                    }
                    else -> {}
                }
            }
        }
        return diggedBlocks
    }
}