package org.cachewrapper.api.server.type;

import org.cachewrapper.network.controller.server.type.ServerType;
import org.cachewrapper.network.controller.server.type.registry.ServerTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class NetfluxServerType {

    public static void register(@NotNull ServerType serverType) {
        ServerTypeRegistry.register(serverType);
    }

    public static <T extends ServerType> T getServerType(@NotNull Class<T> serverTypeClass) {
        return ServerTypeRegistry.get(serverTypeClass);
    }
}