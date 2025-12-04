package org.cachewrapper.velocity.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPostConnectEvent;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.network.controller.server.load.tracker.LoadedServerTracker;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerConnectionListener {

    private final LoadedServerTracker loadedServerTracker;

    @Subscribe
    public void onPlayerPostConnectServer(final @NotNull ServerPostConnectEvent event) {
        var player = event.getPlayer();

        var currentServer = player.getCurrentServer().orElse(null);
        if (currentServer != null) {
            var identifier = currentServer.getServerInfo().getName();
            var updatedOnline = currentServer.getServer().getPlayersConnected().size();

            loadedServerTracker.update(identifier, loadedServer -> loadedServer.setOnlinePlayers(updatedOnline));
        }

        var previousServer = event.getPreviousServer();
        if (previousServer != null) {
            var identifier = previousServer.getServerInfo().getName();
            var updatedOnline = previousServer.getPlayersConnected().size() - 1;

            loadedServerTracker.update(identifier, loadedServer -> loadedServer.setOnlinePlayers(updatedOnline));
        }
    }
}