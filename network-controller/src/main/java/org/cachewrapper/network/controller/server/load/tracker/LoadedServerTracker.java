package org.cachewrapper.network.controller.server.load.tracker;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.cachewrapper.common.insatntiator.impl.RedisInstantiator;
import org.cachewrapper.network.controller.server.load.LoadedServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.redisson.api.RMap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Singleton
public final class LoadedServerTracker {

    private final RMap<String, LoadedServer> loadedServers;

    @Inject
    public LoadedServerTracker(RedisInstantiator redisInstantiator) {
        var redissonClient = redisInstantiator.get();
        this.loadedServers = redissonClient.getMap("loaded_servers");
    }

    public void track(@NotNull LoadedServer loadedServer) {
        var identifier = loadedServer.getServer().getIdentifier();
        loadedServers.fastPut(identifier, loadedServer);
    }

    public void untrack(@NotNull LoadedServer loadedServer) {
        var identifier = loadedServer.getServer().getIdentifier();
        loadedServers.fastRemove(identifier);
    }

    public void update(@NotNull String identifier, @NotNull Consumer<LoadedServer> updateServerConsumer) {
        var loadedServer = this.get(identifier);
        if (loadedServer == null) {
            throw new IllegalStateException("Loaded server with identifier " + identifier + " not found");
        }

        updateServerConsumer.accept(loadedServer);
        loadedServers.fastPut(identifier, loadedServer);
    }

    @Nullable
    public LoadedServer get(@NotNull String identifier) {
        return loadedServers.get(identifier);
    }

    @NotNull
    @UnmodifiableView
    public List<LoadedServer> getAllLoadedServers() {
        return new ArrayList<>(loadedServers.values().stream().toList());
    }

    @NotNull
    @UnmodifiableView
    public List<LoadedServer> getLoadedServersByType(final @NotNull String serverType) {
        return new ArrayList<>(loadedServers
                .values()
                .stream()
                .filter(loadedServer -> loadedServer.getServer().getServerType().equals(serverType))
                .toList());
    }
}