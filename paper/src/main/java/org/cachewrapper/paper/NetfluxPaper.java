package org.cachewrapper.paper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.cachewrapper.common.insatntiator.InstantiatorLoader;
import org.cachewrapper.common.registrar.Registrar;
import org.cachewrapper.common.registrar.RegistrarLoader;
import org.cachewrapper.network.controller.registrar.ServerPickerRegistrar;
import org.cachewrapper.network.controller.registrar.ServerTypeRegistrar;
import org.cachewrapper.paper.guice.PluginModule;
import org.cachewrapper.paper.registrar.bukkit.BukkitListenerRegistrar;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NetfluxPaper extends JavaPlugin {

    private static final List<Class<? extends Registrar>> REGISTRARS = new ArrayList<>(List.of(
            BukkitListenerRegistrar.class,
            ServerPickerRegistrar.class,
            ServerTypeRegistrar.class
    ));

    private Injector injector;

    @Override
    public void onEnable() {
        initGuiceInjector();

        injector.getInstance(InstantiatorLoader.class)
                .loadInstantiators(new ArrayList<>());

        injector.getInstance(RegistrarLoader.class)
                .loadRegistrars(REGISTRARS);
    }

    private void initGuiceInjector() {
        var pluginModule = new PluginModule(this);
        this.injector = Guice.createInjector(pluginModule);
    }
}