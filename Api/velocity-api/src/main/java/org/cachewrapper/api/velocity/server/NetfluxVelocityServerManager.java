package org.cachewrapper.api.velocity.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import org.cachewrapper.api.common.server.NetfluxServerManager;
import org.cachewrapper.api.velocity.server.picker.FirstJoinPicker;
import org.cachewrapper.network.controller.server.load.tracker.LoadedServerTracker;
import org.cachewrapper.network.controller.server.picker.ServerPicker;
import org.jetbrains.annotations.NotNull;

@Getter
@Singleton
public class NetfluxVelocityServerManager extends NetfluxServerManager {

    private FirstJoinPicker firstJoinPicker;

    @Inject
    public NetfluxVelocityServerManager(LoadedServerTracker loadedServerTracker) {
        super(loadedServerTracker);
    }

    public void setFirstJoinPicker(
            @NotNull String serverType,
            @NotNull Class<? extends ServerPicker> serverPickerType
    ) {
        this.firstJoinPicker = new FirstJoinPicker(serverType, serverPickerType);
    }
}