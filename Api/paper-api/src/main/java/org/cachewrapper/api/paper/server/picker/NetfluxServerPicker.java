package org.cachewrapper.api.paper.server.picker;

import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.network.controller.server.picker.ServerPicker;
import org.cachewrapper.network.controller.server.picker.registry.ServerPickerRegistry;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class NetfluxServerPicker {

    private final Injector injector;
    private final ServerPickerRegistry serverPickerRegistry;

    public void register(@NotNull Class<? extends ServerPicker> serverPickerType) {
        if (serverPickerRegistry.get(serverPickerType) != null) {
            throw new IllegalStateException("Server picker already registered");
        }

        var serverPicker = injector.getInstance(serverPickerType);
        serverPickerRegistry.register(serverPicker);
    }

    @NotNull
    public <T extends ServerPicker> T getServerPicker(@NotNull Class<T> serverPickerClass) {
        return serverPickerRegistry.get(serverPickerClass);
    }
}