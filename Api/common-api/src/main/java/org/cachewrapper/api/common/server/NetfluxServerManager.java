package org.cachewrapper.api.common.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.network.controller.server.load.LoadedServer;
import org.cachewrapper.network.controller.server.load.tracker.LoadedServerTracker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@RequiredArgsConstructor
public abstract class NetfluxServerManager {

    private final LoadedServerTracker loadedServerTracker;

    @Nullable
    public LoadedServer getServerByIdentifier(@NotNull String identifier) {
        return this.loadedServerTracker.get(identifier);
    }

    public void setServerMaxOnline(@NotNull String identifier, int maxOnline) {
        this.loadedServerTracker.update(identifier, loadedServer -> loadedServer.getServer().setMaxOnline(maxOnline));
    }

    @NotNull
    public List<LoadedServer> getAllLoadedServers() {
        return this.loadedServerTracker.getAllLoadedServers();
    }
}