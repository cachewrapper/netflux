package org.cachewrapper.network.controller.registrar;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.cachewrapper.network.controller.server.picker.ServerPicker;
import org.cachewrapper.network.controller.server.picker.registry.ServerPickerRegistry;
import org.reflections.Reflections;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerPickerRegistrar implements RegistrarSync {

    private static final String LOOKUP_PACKAGE = "org.cachewrapper.network.controller";

    private final ServerPickerRegistry serverPickerRegistry;
    private final Injector injector;

    @Override
    public void register() {
        var reflections = new Reflections(LOOKUP_PACKAGE);
        var serverPickerClasses = reflections.getSubTypesOf(ServerPicker.class);

        serverPickerClasses.forEach(serverPickerClass -> {
            var serverPicker = injector.getInstance(serverPickerClass);
            serverPickerRegistry.register(serverPicker);
        });
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}
