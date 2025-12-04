package org.cachewrapper.common.packet.sender;

import org.cachewrapper.common.packet.sender.data.PacketSendingData;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public interface PacketSender {

    <T> CompletableFuture<?> send(@NotNull PacketSendingData<T> packetSendingData);
}