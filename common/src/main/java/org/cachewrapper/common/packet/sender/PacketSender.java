package org.cachewrapper.common.packet.sender;

import org.cachewrapper.common.packet.sender.data.PacketSendingData;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface PacketSender {

    <T> CompletableFuture<?> send(@NotNull PacketSendingData<T> packetSendingData);

    default <T> PacketSendingData.Builder<T> builder(
            @NotNull String channel,
            @NotNull T packet
    ) {
        return PacketSendingData.<T>builder(this)
                .channel(channel)
                .sendingPacket(packet);
    }
}