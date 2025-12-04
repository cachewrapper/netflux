package org.cachewrapper.velocity.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.api.velocity.Netflux;
import org.cachewrapper.api.velocity.server.NetfluxVelocityServerManager;
import org.cachewrapper.network.controller.server.load.tracker.LoadedServerTracker;
import org.cachewrapper.network.controller.server.picker.impl.RoundRobinPicker;
import org.cachewrapper.network.controller.server.picker.registry.ServerPickerRegistry;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class PlayerServerPickListener {

    private final ServerPickerRegistry serverPickerRegistry;
    private final LoadedServerTracker loadedServerTracker;
    private final ProxyServer proxyServer;

    @Subscribe
    public void onPlayerConnection(final @NotNull ServerPreConnectEvent event) {
        if (event.getPreviousServer() != null) {
            return;
        }

        NetfluxVelocityServerManager velocityServerManager = Netflux.get().getNetfluxServerManager();
        var firstServerPicker = velocityServerManager.getFirstJoinPicker();

        if (firstServerPicker == null) {
            connectByDefaultServerPicker(event);
            return;
        }

        var playerUUID = event.getPlayer().getUniqueId();
        var serverType = firstServerPicker.serverType();
        var serverPickerType = firstServerPicker.serverPickerType();

        var loadedServer = serverPickerRegistry
                .get(serverPickerType)
                .getServer(playerUUID, loadedServerTracker.getLoadedServersByType(serverType))
                .orElse(null);
        if (loadedServer == null) {
            return;
        }

        var identifier = loadedServer.getServer().getIdentifier();
        var registeredServer = proxyServer.getServer(identifier).orElse(null);
        if (registeredServer == null) {
            return;
        }

        event.setResult(ServerPreConnectEvent.ServerResult.allowed(registeredServer));
    }

    private void connectByDefaultServerPicker(@NotNull ServerPreConnectEvent event) {
        var playerUUID = event.getPlayer().getUniqueId();
        var serverPickerType = serverPickerRegistry.get(RoundRobinPicker.class);

        var loadedServers = loadedServerTracker.getAllLoadedServers();
        var loadedServerOptional = serverPickerType.getServer(playerUUID, loadedServers);
        if (loadedServerOptional.isEmpty()) {
            return;
        }

        var loadedServer = loadedServerOptional.get();
        var registeredServer = proxyServer.getServer(loadedServer.getServer().getIdentifier()).orElse(null);
        if (registeredServer == null) {
            return;
        }

        event.setResult(ServerPreConnectEvent.ServerResult.allowed(registeredServer));
    }
}