package org.cachewrapper.network.controller.network.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.packet.sender.data.PacketSendingData;
import org.cachewrapper.common.packet.sender.impl.RedissonPacketSender;
import org.cachewrapper.network.controller.network.NetworkGateway;
import org.cachewrapper.network.controller.packet.server.ServerSpigotConnectPacket;
import org.cachewrapper.network.controller.packet.server.ServerSpigotDisconnectPacket;
import org.cachewrapper.network.controller.packet.server.ServerSpigotPingPacket;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerNetworkGateway implements NetworkGateway {

    private final RedissonPacketSender jedisPacketSender;

    public CompletableFuture<?> serverSpigotConnect(
            @NotNull String identifier,
            @NotNull String serverType,
            @NotNull InetSocketAddress address,
            int maxOnline
    ) {
        var packet = new ServerSpigotConnectPacket(identifier, serverType, address, maxOnline);
        return PacketSendingData.<ServerSpigotConnectPacket>builder(jedisPacketSender)
                .channel("server.spigot.connect")
                .sendingPacket(packet)
                .send();
    }

    public CompletableFuture<?> serverSpigotPing(
            @NotNull String identifier,
            @NotNull String serverType,
            @NotNull InetSocketAddress address,
            int maxOnline
    ) {
        var packet = new ServerSpigotPingPacket(identifier, serverType, address, maxOnline);
        return PacketSendingData.<ServerSpigotPingPacket>builder(jedisPacketSender)
                .channel("server.spigot.ping")
                .sendingPacket(packet)
                .send();
    }

    public CompletableFuture<?> serverSpigotDisconnect(@NotNull String identifier) {
        var packet = new ServerSpigotDisconnectPacket(identifier);
        return PacketSendingData.<ServerSpigotDisconnectPacket>builder(jedisPacketSender)
                .channel("server.spigot.disconnect")
                .sendingPacket(packet)
                .send();
    }
}