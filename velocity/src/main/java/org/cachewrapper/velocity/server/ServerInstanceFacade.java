package org.cachewrapper.velocity.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.network.controller.server.Server;
import org.cachewrapper.network.controller.server.load.LoadedServer;
import org.cachewrapper.network.controller.server.load.tracker.LoadedServerTracker;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerInstanceFacade {

    private final LoadedServerTracker loadedServerTracker;
    private final ProxyServer proxyServer;

    public void create(
            @NotNull String identifier,
            @NotNull String serverType,
            @NotNull InetSocketAddress address,
            int maxOnline
    ) {
        var server = Server.builder()
                .identifier(identifier)
                .serverType(serverType)
                .address(address)
                .maxOnline(maxOnline)
                .build();

        var loadedServer = new LoadedServer(server);
        var serverInfo = new ServerInfo(identifier, address);

        proxyServer.registerServer(serverInfo);
        loadedServerTracker.track(loadedServer);
    }

    public void load(@NotNull String identifier) {
        var loadedServer = loadedServerTracker.get(identifier);
        if (loadedServer == null) {
            return;
        }

        var address = loadedServer.getServer().getAddress();
        var serverInfo = new ServerInfo(identifier, address);

        proxyServer.registerServer(serverInfo);
    }

    public void updatePingTime(@NotNull String identifier) {
        var loadedServer = loadedServerTracker.get(identifier);
        if (loadedServer == null) {
            return;
        }

        var updatedLoadedServer = new LoadedServer(loadedServer.getServer());
        loadedServerTracker.track(updatedLoadedServer);
    }

    public void remove(@NotNull String identifier) {
        var loadedServer = loadedServerTracker.get(identifier);
        if (loadedServer != null) {
            loadedServerTracker.untrack(loadedServer);
        }

        var registeredServer = proxyServer.getServer(identifier).orElse(null);
        if (registeredServer != null) {
            var serverInfo = registeredServer.getServerInfo();
            proxyServer.unregisterServer(serverInfo);
        }
    }
}