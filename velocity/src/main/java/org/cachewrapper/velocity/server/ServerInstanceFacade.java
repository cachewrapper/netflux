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

    private final ProxyServer proxyServer;
    private final LoadedServerTracker loadedServerTracker;

    public void create(@NotNull String identifier, @NotNull InetSocketAddress address) {
        var server = Server.builder()
                .identifier(identifier)
                .address(address)
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

        loadedServer.setLastPingMs(System.currentTimeMillis());
        loadedServerTracker.track(loadedServer);
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