package org.cachewrapper.network.controller.server.picker.impl;

import org.cachewrapper.network.controller.server.load.LoadedServer;
import org.cachewrapper.network.controller.server.picker.ServerPicker;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinPicker extends ServerPicker {

    private final AtomicInteger lastPickedServerIndex = new AtomicInteger(0);

    @Override
    public Optional<LoadedServer> getServer(@NotNull UUID playerUUID, @NotNull List<LoadedServer> loadedServers) {
        if (loadedServers.isEmpty()) {
            return Optional.empty();
        }

        int index = lastPickedServerIndex.getAndUpdate(it -> (it + 1) % loadedServers.size());
        return Optional.of(loadedServers.get(index));
    }
}
