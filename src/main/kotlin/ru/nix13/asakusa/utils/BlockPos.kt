package ru.nix13.asakusa.utils

class BlockPos(val x: Int, val y: Int, val z: Int) {
    override fun toString(): String {
        return "$x, $y, $z"
    }
}