package org.cachewrapper.paper.registrar.bukkit;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.cachewrapper.paper.NetfluxPaper;
import org.reflections.Reflections;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class BukkitListenerRegistrar implements RegistrarSync {

    private static final String LOOKUP_PACKAGE = "org.cachewrapper.paper";

    private final NetfluxPaper plugin;
    private final Injector injector;

    @Override
    public void register() {
        var reflections = new Reflections(LOOKUP_PACKAGE);
        var listenerClasses = reflections.getSubTypesOf(Listener.class);

        listenerClasses.forEach(listenerClass -> {
            var listener = injector.getInstance(listenerClass);
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        });
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}