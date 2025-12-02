package org.cachewrapper.network.controller.server.load.tracker;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.network.controller.server.load.LoadedServer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class LoadedServerTracker {

    private final Map<String, LoadedServer> loadedServers = new ConcurrentHashMap<>();

    public void track(@NotNull LoadedServer loadedServer) {
        var identifier = loadedServer.getServer().getIdentifier();
        loadedServers.put(identifier, loadedServer);
    }

    public void untrack(@NotNull LoadedServer loadedServer) {
        var identifier = loadedServer.getServer().getIdentifier();
        loadedServers.remove(identifier);
    }

    public void untrack(@NotNull String identifier) {
        loadedServers.remove(identifier);
    }

    public LoadedServer get(@NotNull String identifier) {
        return loadedServers.get(identifier);
    }
}
