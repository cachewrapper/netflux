package org.cachewrapper.api.velocity.provider;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.api.velocity.server.NetfluxVelocityServerManager;
import org.cachewrapper.network.controller.network.player.PlayerNetworkGateway;

@Getter
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class NetfluxVelocityProvider {

    private final PlayerNetworkGateway playerNetworkGateway;
    private final NetfluxVelocityServerManager netfluxServerManager;
}
