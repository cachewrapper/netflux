package org.cachewrapper.velocity.packet.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.packet.listener.PacketListener;
import org.cachewrapper.network.controller.packet.server.ServerSpigotPingPacket;
import org.cachewrapper.network.controller.server.load.tracker.LoadedServerTracker;
import org.cachewrapper.velocity.server.ServerInstanceFacade;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerSpigotPingListener extends PacketListener<ServerSpigotPingPacket> {

    private final ServerInstanceFacade serverInstanceFacade;
    private final LoadedServerTracker loadedServerTracker;
    private final ProxyServer proxyServer;

    @Override
    public @NotNull Class<ServerSpigotPingPacket> getPacketType() {
        return ServerSpigotPingPacket.class;
    }

    @Override
    public @NotNull String getChannel() {
        return "server.spigot.ping";
    }

    @Override
    public void onPacket(@NotNull ServerSpigotPingPacket packet) {
        var identifier = packet.identifier();
        var loadedServer = loadedServerTracker.get(identifier);

        if (loadedServer == null) {
            var address = packet.address();

            serverInstanceFacade.create(identifier, address);
            return;
        }

        var registeredServer = proxyServer.getServer(identifier).orElse(null);
        if (registeredServer == null) {
            serverInstanceFacade.load(identifier);
            return;
        }

        serverInstanceFacade.updatePingTime(identifier);
    }
}