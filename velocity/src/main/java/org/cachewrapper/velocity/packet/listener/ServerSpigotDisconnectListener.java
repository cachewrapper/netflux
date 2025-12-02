package org.cachewrapper.velocity.packet.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.packet.listener.PacketListener;
import org.cachewrapper.network.controller.packet.server.ServerSpigotDisconnectPacket;
import org.cachewrapper.velocity.server.ServerInstanceFacade;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerSpigotDisconnectListener extends PacketListener<ServerSpigotDisconnectPacket> {

    private final ServerInstanceFacade serverInstanceFacade;

    @Override
    public @NotNull Class<ServerSpigotDisconnectPacket> getPacketType() {
        return ServerSpigotDisconnectPacket.class;
    }

    @Override
    public @NotNull String getChannel() {
        return "server.spigot.disconnect";
    }

    @Override
    public void onPacket(@NotNull ServerSpigotDisconnectPacket packet) {
        var identifier = packet.identifier();
        serverInstanceFacade.remove(identifier);
    }
}
