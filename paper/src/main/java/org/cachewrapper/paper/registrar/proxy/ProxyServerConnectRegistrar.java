package org.cachewrapper.paper.registrar.proxy;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.cachewrapper.common.config.ConfigProvider;
import org.cachewrapper.common.config.type.ServerSettingsConfig;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.cachewrapper.network.controller.network.server.ServerNetworkGateway;
import org.cachewrapper.paper.NetfluxPaper;
import org.cachewrapper.paper.server.ServerHeartbeat;

import java.net.InetSocketAddress;
import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ProxyServerConnectRegistrar implements RegistrarSync {

    private static final String IDENTIFIER_FORMAT = "%s_%s";

    private final ServerNetworkGateway serverNetworkGateway;
    private final NetfluxPaper plugin;
    private final Injector injector;

    @Override
    public void register() {
        var serverType = ConfigProvider.get(ServerSettingsConfig.class, injector)
                .path("server.server-type")
                .get(String.class);

        var identifier = IDENTIFIER_FORMAT.formatted(serverType, UUID.randomUUID());
        plugin.setIdentifier(identifier);
        plugin.setServerType(serverType);

        var address = new InetSocketAddress(Bukkit.getIp(), Bukkit.getPort());
        var maxOnline = Bukkit.getMaxPlayers();

        serverNetworkGateway
                .serverSpigotConnect(plugin.getIdentifier(), serverType, address, maxOnline)
                .thenRun(() -> ServerHeartbeat.runTask(injector));
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}