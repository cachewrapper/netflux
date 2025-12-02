package org.cachewrapper.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.cachewrapper.common.insatntiator.InstantiatorLoader;
import org.cachewrapper.common.registrar.RegistrarLoader;
import org.cachewrapper.velocity.server.ServerInstanceFacade;

import java.util.ArrayList;
import java.util.logging.Logger;

@Getter
@Plugin(id = "netflux-velocity")
public class NetfluxVelocity {

    private final Injector injector;

    private final ProxyServer proxyServer;
    private final Logger logger;

    @Inject
    public NetfluxVelocity(Injector injector, ProxyServer proxyServer, Logger logger) {
        this.injector = injector;

        this.proxyServer = proxyServer;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        injector.getInstance(InstantiatorLoader.class)
                .loadInstantiators(new ArrayList<>());

        injector.getInstance(RegistrarLoader.class)
                .loadRegistrars(new ArrayList<>());
    }
}
