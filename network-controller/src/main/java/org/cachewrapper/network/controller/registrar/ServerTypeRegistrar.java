package org.cachewrapper.network.controller.registrar;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.cachewrapper.network.controller.server.type.ServerType;
import org.cachewrapper.network.controller.server.type.registry.ServerTypeRegistry;
import org.reflections.Reflections;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerTypeRegistrar implements RegistrarSync {

    private static final String LOOKUP_PACKAGE = "org.cachewrapper.network.controller";

    private final Injector injector;

    @Override
    public void register() {
        var reflections = new Reflections(LOOKUP_PACKAGE);
        var serverTypeClasses = reflections.getSubTypesOf(ServerType.class);

        serverTypeClasses.forEach(serverTypeClass -> {
            var serverType = injector.getInstance(serverTypeClass);
            ServerTypeRegistry.register(serverType);
        });
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}