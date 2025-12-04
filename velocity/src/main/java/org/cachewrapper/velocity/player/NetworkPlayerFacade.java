package org.cachewrapper.velocity.player;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class NetworkPlayerFacade {

    private final ProxyServer proxyServer;

    public void connectByIdentifier(
            @NotNull UUID playerUUID,
            @NotNull String identifier
    ) {
        var player = proxyServer.getPlayer(playerUUID).orElse(null);
        if (player == null) {
            return;
        }

        var loadedServer = proxyServer.getServer(identifier).orElse(null);
        if (loadedServer == null) {
            return;
        }

        player.createConnectionRequest(loadedServer).connect();
    }
}