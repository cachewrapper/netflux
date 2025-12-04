package org.cachewrapper.network.controller.server.load;

import lombok.Data;
import org.cachewrapper.network.controller.server.Server;

@Data
public class LoadedServer {

    private final Server server;
    private final long lastPingMs = System.currentTimeMillis();

    private int onlinePlayers = 0;
}