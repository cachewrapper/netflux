package org.cachewrapper.velocity.registrar.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.velocitypowered.api.event.Subscribe;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.cachewrapper.velocity.NetfluxVelocity;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Method;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class VelocityListenerRegistrar implements RegistrarSync {

    private static final String LOOKUP_PACKAGE = "org.cachewrapper.velocity";

    private final NetfluxVelocity plugin;
    private final Injector injector;

    @Override
    public void register() {
        var reflections = new Reflections(LOOKUP_PACKAGE, Scanners.MethodsAnnotated);
        var methods = reflections.getMethodsAnnotatedWith(Subscribe.class);

        methods.stream()
                .map(Method::getDeclaringClass)
                .filter(listenerClass -> !NetfluxVelocity.class.isAssignableFrom(listenerClass))
                .distinct()
                .forEach(listenerClass -> {
                    var listener = this.injector.getInstance(listenerClass);
                    this.plugin.getProxyServer().getEventManager().register(this.plugin, listener);
                });
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}
