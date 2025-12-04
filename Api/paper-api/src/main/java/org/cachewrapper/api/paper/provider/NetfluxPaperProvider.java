package org.cachewrapper.api.paper.provider;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.api.paper.server.NetfluxPaperServerManager;
import org.cachewrapper.api.paper.server.picker.NetfluxServerPicker;
import org.cachewrapper.network.controller.network.player.PlayerNetworkGateway;

@Getter
@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class NetfluxPaperProvider {

    private final PlayerNetworkGateway playerNetworkGateway;
    private final NetfluxServerPicker netfluxServerPicker;
    private final NetfluxPaperServerManager netfluxServerManager;
}
