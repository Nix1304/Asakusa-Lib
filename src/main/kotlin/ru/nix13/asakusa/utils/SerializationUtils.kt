package ru.nix13.asakusa.utils

import java.io.*

object SerializationUtils {
    fun classToByteArray(cls: Any): ByteArray {
        val bos = ByteArrayOutputStream()
        val out: ObjectOutputStream
        bos.use {
            out = ObjectOutputStream(it)
            out.writeObject(cls)
            out.flush()
            return it.toByteArray()
        }
    }

    fun <T>byteArrayToClass(array: ByteArray): T {
        val bis = ByteArrayInputStream(array)
        var objInput: ObjectInput? = null
        try {
            objInput = ObjectInputStream(bis)
            return objInput.readObject() as T
        } finally {
            objInput?.close()
        }
    }
}