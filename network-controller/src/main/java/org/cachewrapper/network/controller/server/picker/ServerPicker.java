package org.cachewrapper.network.controller.server.picker;

import org.cachewrapper.network.controller.server.load.LoadedServer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class ServerPicker {

    public abstract Optional<LoadedServer> getServer(
            @NotNull UUID playerUUID,
            @NotNull List<LoadedServer> loadedServers
    );
}