package org.cachewrapper.paper.server;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.cachewrapper.network.controller.network.server.ServerNetworkGateway;
import org.cachewrapper.paper.NetfluxPaper;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerHeartbeat implements Runnable {

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    private static final Duration SERVER_HEARTBEAT_PERIOD = Duration.ofSeconds(5);

    private final ServerNetworkGateway serverNetworkGateway;
    private final NetfluxPaper plugin;

    public static void runTask(final @NotNull Injector injector) {
        var serverHeartbeat = injector.getInstance(ServerHeartbeat.class);
        SCHEDULER.scheduleAtFixedRate(serverHeartbeat, SERVER_HEARTBEAT_PERIOD.toSeconds(), SERVER_HEARTBEAT_PERIOD.toSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        var identifier = plugin.getIdentifier();
        var serverType = plugin.getServerType();

        var address = new InetSocketAddress(Bukkit.getIp(), Bukkit.getPort());
        var maxOnline = Bukkit.getMaxPlayers();

        serverNetworkGateway.serverSpigotPing(identifier, serverType, address, maxOnline);
    }
}