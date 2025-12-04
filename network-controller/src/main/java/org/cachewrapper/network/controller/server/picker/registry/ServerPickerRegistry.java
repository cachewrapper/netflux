package org.cachewrapper.network.controller.server.picker.registry;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.network.controller.server.picker.ServerPicker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerPickerRegistry {

    private final Map<Class<? extends ServerPicker>, ServerPicker> serverPickers = new HashMap<>();

    public void register(@NotNull ServerPicker serverPicker) {
        serverPickers.put(serverPicker.getClass(), serverPicker);
    }

    public <T extends ServerPicker> T get(Class<T> serverPickerClass) {
        return serverPickerClass.cast(serverPickers.get(serverPickerClass));
    }

    @UnmodifiableView
    public List<ServerPicker> getAllServerPickers() {
        return serverPickers.values().stream().toList();
    }
}