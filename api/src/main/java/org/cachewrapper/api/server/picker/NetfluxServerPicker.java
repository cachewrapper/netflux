package org.cachewrapper.api.server.picker;

import org.cachewrapper.network.controller.server.picker.ServerPicker;
import org.cachewrapper.network.controller.server.picker.registry.ServerPickerRegistry;
import org.jetbrains.annotations.NotNull;

public class NetfluxServerPicker {

    public static void register(@NotNull ServerPicker serverPicker) {
        ServerPickerRegistry.register(serverPicker);
    }

    public static <T extends ServerPicker> T getServerPicker(@NotNull Class<T> serverPickerClass) {
        return ServerPickerRegistry.get(serverPickerClass);
    }
}