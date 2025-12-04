package org.cachewrapper.api.paper.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.cachewrapper.api.common.server.NetfluxServerManager;
import org.cachewrapper.network.controller.server.load.tracker.LoadedServerTracker;

@Singleton
public class NetfluxPaperServerManager extends NetfluxServerManager {

    @Inject
    public NetfluxPaperServerManager(LoadedServerTracker loadedServerTracker) {
        super(loadedServerTracker);
    }
}
