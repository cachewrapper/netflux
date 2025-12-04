package org.cachewrapper.velocity.player.packet.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.packet.listener.PacketListener;
import org.cachewrapper.network.controller.packet.player.PlayerSendServerPacket;
import org.cachewrapper.velocity.player.NetworkPlayerFacade;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class PlayerSendServerSpecificListener extends PacketListener<PlayerSendServerPacket> {

    private final NetworkPlayerFacade networkPlayerFacade;

    @Override
    public @NotNull Class<PlayerSendServerPacket> getPacketType() {
        return PlayerSendServerPacket.class;
    }

    @Override
    public @NotNull String getChannel() {
        return "player.server.send";
    }

    @Override
    public void onPacket(@NotNull PlayerSendServerPacket packet) {
        var identifier = packet.identifier();
        var playerUUID = packet.playerUUID();

        networkPlayerFacade.connectByIdentifier(playerUUID, identifier);
    }
}