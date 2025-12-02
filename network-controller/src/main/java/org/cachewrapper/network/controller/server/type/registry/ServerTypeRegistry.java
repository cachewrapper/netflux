package org.cachewrapper.network.controller.server.type.registry;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.network.controller.server.type.ServerType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerTypeRegistry {

    private static final Map<Class<? extends ServerType>, ServerType> serverTypes = new HashMap<>();

    public static void register(@NotNull ServerType serverType) {
        serverTypes.put(serverType.getClass(), serverType);
    }

    @NotNull
    public static  <T extends ServerType> T get(@NotNull Class<T> serverTypeClass) {
        var serverType = serverTypes.get(serverTypeClass);
        return serverTypeClass.cast(serverType);
    }
}