package org.cachewrapper.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.cachewrapper.common.config.Config;
import org.cachewrapper.common.config.type.ServerSettingsConfig;
import org.cachewrapper.common.registrar.Registrar;
import org.cachewrapper.common.registrar.RegistrarLoader;
import org.cachewrapper.network.controller.registrar.ServerPickerRegistrar;
import org.cachewrapper.velocity.registrar.ConfigRegistrar;
import org.cachewrapper.velocity.registrar.NetfluxVelocityApiRegistrar;
import org.cachewrapper.velocity.registrar.velocity.VelocityListenerRegistrar;
import org.cachewrapper.velocity.server.task.ServerHealthMonitor;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
@Plugin(id = "netflux-velocity")
public class NetfluxVelocity {

    private static final List<Class<? extends Registrar>> REGISTRARS = new ArrayList<>(List.of(
            ConfigRegistrar.class,
            NetfluxVelocityApiRegistrar.class,
            ServerPickerRegistrar.class,
            VelocityListenerRegistrar.class
    ));

    private final List<Class<? extends Config>> configs = new ArrayList<>(List.of(
            ServerSettingsConfig.class
    ));

    private final Injector injector;
    private final Path dataDirectory;

    private final ProxyServer proxyServer;
    private final Logger logger;

    @Inject
    public NetfluxVelocity(
            @NotNull Injector injector,
            @NotNull ProxyServer proxyServer,
            @NotNull Logger logger,
            @DataDirectory @NotNull Path dataDirectory
    ) {
        this.injector = injector;
        this.dataDirectory = dataDirectory;

        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        injector.getInstance(RegistrarLoader.class)
                .loadRegistrars(REGISTRARS);

        ServerHealthMonitor.runTask(injector);
    }
}
