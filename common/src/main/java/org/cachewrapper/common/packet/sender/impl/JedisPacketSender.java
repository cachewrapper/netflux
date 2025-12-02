package org.cachewrapper.common.packet.sender.impl;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.insatntiator.impl.RedisInstantiator;
import org.cachewrapper.common.packet.sender.PacketSender;
import org.cachewrapper.common.packet.sender.data.PacketSendingData;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public final class JedisPacketSender implements PacketSender {

    private static final Gson GSON = new Gson();

    private final RedisInstantiator redisInstantiator;

    @Override
    public <T> CompletableFuture<Void> send(@NotNull PacketSendingData<T> packetSendingData) {
        return CompletableFuture.runAsync(() -> {
            var jedisPool = redisInstantiator.get();

            var packet = packetSendingData.sendingPacket();
            var channel = packetSendingData.channel();
            var serializedPacket = GSON.toJson(packet, packet.getClass());

            try (var jedis = jedisPool.getResource()) {
                jedis.publish(channel, serializedPacket);
            }
        });
    }
}