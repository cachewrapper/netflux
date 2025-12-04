package org.cachewrapper.paper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.cachewrapper.common.config.Config;
import org.cachewrapper.common.config.type.ServerSettingsConfig;
import org.cachewrapper.common.registrar.Registrar;
import org.cachewrapper.common.registrar.RegistrarLoader;
import org.cachewrapper.network.controller.network.server.ServerNetworkGateway;
import org.cachewrapper.network.controller.registrar.ServerPickerRegistrar;
import org.cachewrapper.paper.guice.PluginModule;
import org.cachewrapper.paper.registrar.ConfigRegistrar;
import org.cachewrapper.paper.registrar.NetfluxPaperApiRegistrar;
import org.cachewrapper.paper.registrar.bukkit.BukkitListenerRegistrar;
import org.cachewrapper.paper.registrar.proxy.ProxyServerConnectRegistrar;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NetfluxPaper extends JavaPlugin {

    private static final List<Class<? extends Registrar>> REGISTRARS = new ArrayList<>(List.of(
            ConfigRegistrar.class,
            NetfluxPaperApiRegistrar.class,
            ServerPickerRegistrar.class,
            BukkitListenerRegistrar.class,
            ProxyServerConnectRegistrar.class
    ));

    private final List<Class<? extends Config>> configs = new ArrayList<>(List.of(
            ServerSettingsConfig.class
    ));

    @Setter
    private String identifier;

    @Setter
    private String serverType;

    private Injector injector;

    @Override
    public void onEnable() {
        initGuiceInjector();
        injector.getInstance(RegistrarLoader.class)
                .loadRegistrars(REGISTRARS);
    }

    @Override
    public void onDisable() {
        injector.getInstance(ServerNetworkGateway.class).serverSpigotDisconnect(identifier);
    }

    private void initGuiceInjector() {
        var pluginModule = new PluginModule(this);
        this.injector = Guice.createInjector(pluginModule);
    }
}