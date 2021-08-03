package ru.nix13.asakusa.network

import cpw.mods.fml.common.network.simpleimpl.IMessage
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler
import cpw.mods.fml.common.network.simpleimpl.MessageContext
import cpw.mods.fml.relauncher.Side
import io.netty.buffer.ByteBuf
import net.minecraft.client.network.NetHandlerPlayClient
import net.minecraft.network.NetHandlerPlayServer
import ru.nix13.asakusa.utils.SerializationUtils.byteArrayToClass
import ru.nix13.asakusa.utils.SerializationUtils.classToByteArray
import java.io.Serializable

open class BaseMessage<T: Any>: IMessage, Serializable {
    var data: T? = null

    override fun toBytes(buf: ByteBuf) {
        val byteArray = classToByteArray(this)
        buf.writeInt(byteArray.size)
        buf.writeBytes(byteArray)
    }

    override fun fromBytes(buf: ByteBuf) {
        val size = buf.readInt()
        val array = buf.readBytes(size).array()
        data = byteArrayToClass<T>(array)
    }
}

open class BaseMessageHandler<T: BaseMessage<T>>: IMessageHandler<T, IMessage> {
    override fun onMessage(msg: T, ctx: MessageContext): IMessage? {
        if(ctx.side == Side.SERVER) server(msg, ctx.serverHandler)
        if(ctx.side == Side.CLIENT) client(msg, ctx.clientHandler)
        return null
    }

    open fun client(msg: T, handler: NetHandlerPlayClient){}
    open fun server(msg: T, handler: NetHandlerPlayServer){}
}