package ru.nix13.asakusa.network

import cpw.mods.fml.common.FMLLog
import cpw.mods.fml.common.FMLModContainer
import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.discovery.ASMDataTable
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.network.simpleimpl.IMessage
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper
import cpw.mods.fml.relauncher.Side
import ru.nix13.asakusa.AsakusaLib.Companion.modId

data class PacketToRegister(val packet: Class<IMessage>, val handler: Class<out IMessageHandler<IMessage, IMessage>>, val side: Array<out Side>)

object NetworkManager {
    val networkInstance: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(modId)
    private var id = 0
    private val packetsToRegister = ArrayList<PacketToRegister>()

    fun searchPackets(asmHarvestedData: ASMDataTable) {
        val mods = Loader.instance().modList
        val mcl = Loader.instance().modClassLoader

        mods.filterIsInstance<FMLModContainer>().forEach { mod ->
            FMLLog.getLogger().debug("Attempting to inject @Packet classes into the eventbus for {}", mod.modId)
            val modData = asmHarvestedData.getAnnotationsFor(mod)
            val targets = modData.get(Packet::class.java.name)

            targets.forEach { target ->
                FMLLog.getLogger().debug("Registering @Packet for {} for mod {}", target.className, mod.modId)

                val packet = Class.forName(target.className, false, mcl) as Class<IMessage>
                val packetHandler = packet.classes.first() as Class<IMessageHandler<IMessage, IMessage>>
                val side = packet.annotations.filterIsInstance(Packet::class.java).first().side

                packetsToRegister.add(PacketToRegister(packet, packetHandler, side))

                FMLLog.getLogger().info("Injected @Packet class {} with handler {} on side: [{}]", target.className, packetHandler.name, side.joinToString(", "))
            }
        }
    }

    fun registerPackets() {
        packetsToRegister.forEach { packet ->
            packet.side.forEach { side ->
                registerMessage(packet.handler, packet.packet, id++, side)
            }
        }
    }

    private fun registerMessage(messageHandler: Class<out IMessageHandler<IMessage, IMessage>>, requestMessageType: Class<IMessage>, discriminator: Int, side: Side) {
        networkInstance.registerMessage(messageHandler, requestMessageType, discriminator, side)
    }
}