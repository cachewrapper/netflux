package org.cachewrapper.velocity.server.packet.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cachewrapper.common.packet.listener.PacketListener;
import org.cachewrapper.network.controller.packet.server.ServerSpigotConnectPacket;
import org.cachewrapper.velocity.server.ServerInstanceFacade;
import org.jetbrains.annotations.NotNull;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerSpigotConnectListener extends PacketListener<ServerSpigotConnectPacket> {

    private final ServerInstanceFacade serverInstanceFacade;
    private final ProxyServer proxyServer;

    @Override
    public @NotNull Class<ServerSpigotConnectPacket> getPacketType() {
        return ServerSpigotConnectPacket.class;
    }

    @Override
    public @NotNull String getChannel() {
        return "server.spigot.connect";
    }

    @Override
    public void onPacket(@NotNull ServerSpigotConnectPacket packet) {
        var identifier = packet.identifier();

        var registeredServer = proxyServer.getServer(identifier).orElse(null);
        if (registeredServer != null) {
            serverInstanceFacade.load(identifier);
            return;
        }

        var address = packet.address();
        var serverType = packet.serverType();
        var maxOnline = packet.maxOnline();

        serverInstanceFacade.create(identifier, serverType, address, maxOnline);
    }
}