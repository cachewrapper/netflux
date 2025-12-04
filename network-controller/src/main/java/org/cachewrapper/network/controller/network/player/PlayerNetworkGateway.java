package org.cachewrapper.network.controller.network.player;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.packet.sender.data.PacketSendingData;
import org.cachewrapper.common.packet.sender.impl.RedissonPacketSender;
import org.cachewrapper.network.controller.network.NetworkGateway;
import org.cachewrapper.network.controller.packet.player.PlayerSendServerPacket;
import org.cachewrapper.network.controller.server.load.tracker.LoadedServerTracker;
import org.cachewrapper.network.controller.server.picker.ServerPicker;
import org.cachewrapper.network.controller.server.picker.registry.ServerPickerRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class PlayerNetworkGateway implements NetworkGateway {

    private final LoadedServerTracker loadedServerTracker;
    private final ServerPickerRegistry serverPickerRegistry;

    private final RedissonPacketSender jedisPacketSender;

    public CompletableFuture<?> connectPlayer(
            @NotNull UUID playerUUID,
            @NotNull String identifier
    ) {
        var packet = new PlayerSendServerPacket(playerUUID, identifier);
        return PacketSendingData.<PlayerSendServerPacket>builder(jedisPacketSender)
                .channel("player.server.send")
                .sendingPacket(packet)
                .send();
    }

    public CompletableFuture<?> connectPlayer(
            @NotNull UUID playerUUID,
            @NotNull String serverType,
            @NotNull Class<? extends ServerPicker> serverPickerType
    ) {
        var serverPicker = serverPickerRegistry.get(serverPickerType);
        if (serverPicker == null) {
            throw new RuntimeException("Server picker not found for type " + serverPickerType);
        }

        var loadedServerList = loadedServerTracker.getLoadedServersByType(serverType);
        var loadedServerOptional = serverPicker.getServer(playerUUID, loadedServerList);

        if (loadedServerOptional.isEmpty()) {
            throw new RuntimeException("Server not found for type " + serverType);
        }

        var identifier = loadedServerOptional.get().getServer().getIdentifier();
        var packet = new PlayerSendServerPacket(playerUUID, identifier);

        return PacketSendingData.<PlayerSendServerPacket>builder(jedisPacketSender)
                .channel("player.server.send")
                .sendingPacket(packet)
                .send();
    }
}