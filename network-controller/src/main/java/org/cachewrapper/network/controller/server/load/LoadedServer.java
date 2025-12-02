package org.cachewrapper.network.controller.server.load;

import lombok.Builder;
import lombok.Data;
import org.cachewrapper.network.controller.server.Server;

@Data
public class LoadedServer {

    private final Server server;

    private int onlinePlayers = 0;
    private long lastPingMs = System.currentTimeMillis();
}