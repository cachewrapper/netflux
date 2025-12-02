package org.cachewrapper.common.packet.sender.data;

import org.cachewrapper.common.packet.sender.PacketSender;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public record PacketSendingData<T>(
        String channel,
        T sendingPacket
) {

    public static <T> PacketSendingData.Builder<T> builder(
            @NotNull PacketSender packetSender
    ) {
        return new PacketSendingData.Builder<>(packetSender);
    }

    public static class Builder<T> {

        private final PacketSender packetSender;

        private String channel;
        private T sendingPacket;

        public Builder(PacketSender packetSender) {
            this.packetSender = packetSender;
        }

        public Builder<T> channel(String channel) {
            this.channel = channel;
            return this;
        }

        public Builder<T> sendingPacket(T sendingPacket) {
            this.sendingPacket = sendingPacket;
            return this;
        }

        public CompletableFuture<?> send() {
            return packetSender.send(build());
        }

        public PacketSendingData<T> build() {
            return new PacketSendingData<>(channel, sendingPacket);
        }
    }
}