package org.cachewrapper.paper.registrar.proxy;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.cachewrapper.network.controller.network.server.ServerNetworkGateway;
import org.cachewrapper.network.controller.server.type.ServerType;
import org.cachewrapper.network.controller.server.type.impl.UnknownServerType;

import java.net.InetSocketAddress;
import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ProxyServerConnectRegistrar implements RegistrarSync {

    private final ServerNetworkGateway serverNetworkGateway;

    @Override
    public void register() {
        var identifier = UUID.randomUUID().toString();
        var serverType = UnknownServerType.class;

        var address = new InetSocketAddress(Bukkit.getIp(), Bukkit.getPort());
        var maxPlayers = Bukkit.getMaxPlayers();

        serverNetworkGateway.serverSpigotConnect(identifier, serverType, address, maxPlayers);
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}