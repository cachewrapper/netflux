package org.cachewrapper.velocity.server.task;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.network.controller.server.load.tracker.LoadedServerTracker;
import org.cachewrapper.velocity.server.ServerInstanceFacade;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerHealthMonitor implements Runnable {

    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
    private static final Duration SERVER_HEALTH_PERIOD = Duration.ofSeconds(10);

    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    private final LoadedServerTracker loadedServerTracker;
    private final ServerInstanceFacade serverInstanceFacade;

    public static void runTask(final @NotNull Injector injector) {
        var serverHealthMonitor = injector.getInstance(ServerHealthMonitor.class);
        SCHEDULER.scheduleAtFixedRate(serverHealthMonitor, SERVER_HEALTH_PERIOD.toSeconds(), SERVER_HEALTH_PERIOD.toSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        var servers = new ArrayList<>(loadedServerTracker.getAllLoadedServers());

        for (var loadedServer : servers) {
            long elapsedMs = System.currentTimeMillis() - loadedServer.getLastPingMs();

            if (elapsedMs > TIMEOUT.toMillis()) {
                var identifier = loadedServer.getServer().getIdentifier();
                this.serverInstanceFacade.remove(identifier);
            }
        }
    }
}