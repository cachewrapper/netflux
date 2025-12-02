package org.cachewrapper.network.controller.network.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.packet.sender.data.PacketSendingData;
import org.cachewrapper.common.packet.sender.impl.JedisPacketSender;
import org.cachewrapper.network.controller.network.NetworkGateway;
import org.cachewrapper.network.controller.packet.server.ServerSpigotConnectPacket;
import org.cachewrapper.network.controller.packet.server.ServerSpigotDisconnectPacket;
import org.cachewrapper.network.controller.packet.server.ServerSpigotPingPacket;
import org.cachewrapper.network.controller.server.type.ServerType;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerNetworkGateway implements NetworkGateway {

    private final JedisPacketSender jedisPacketSender;

    public void serverSpigotConnect(
            @NotNull String identifier,
            @NotNull Class<? extends ServerType> serverType,
            @NotNull InetSocketAddress address,
            int maxPlayers
    ) {
        var packet = new ServerSpigotConnectPacket(identifier, serverType, address, maxPlayers);
        PacketSendingData.<ServerSpigotConnectPacket>builder(jedisPacketSender)
                .channel("server.spigot.connect")
                .sendingPacket(packet)
                .send();
    }

    public void serverSpigotPing(
            @NotNull String identifier,
            @NotNull Class<? extends ServerType> serverType,
            @NotNull InetSocketAddress address,
            int maxPlayers
    ) {
        var packet = new ServerSpigotPingPacket(identifier, serverType, address, maxPlayers);
        PacketSendingData.<ServerSpigotPingPacket>builder(jedisPacketSender)
                .channel("server.spigot.ping")
                .sendingPacket(packet)
                .send();
    }

    public void serverSpigotDisconnect(
            @NotNull String identifier
    ) {
        var packet = new ServerSpigotDisconnectPacket(identifier);
        PacketSendingData.<ServerSpigotDisconnectPacket>builder(jedisPacketSender)
                .channel("server.spigot.disconnect")
                .sendingPacket(packet)
                .send();
    }
}