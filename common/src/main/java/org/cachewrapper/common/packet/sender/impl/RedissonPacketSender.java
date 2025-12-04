package org.cachewrapper.common.packet.sender.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cachewrapper.common.gson.GsonClassTypeAdapter;
import org.cachewrapper.common.gson.InetSocketAddressAdapter;
import org.cachewrapper.common.insatntiator.impl.RedisInstantiator;
import org.cachewrapper.common.packet.sender.PacketSender;
import org.cachewrapper.common.packet.sender.data.PacketSendingData;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RTopic;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public final class RedissonPacketSender implements PacketSender {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Class.class, new GsonClassTypeAdapter())
            .registerTypeAdapter(InetSocketAddress.class, new InetSocketAddressAdapter())
            .create();

    private final RedisInstantiator redisInstantiator;

    @Override
    public <T> CompletableFuture<Void> send(@NotNull PacketSendingData<T> packetSendingData) {
        return CompletableFuture.runAsync(() -> {
            var redissonClient = redisInstantiator.get();

            var packet = packetSendingData.sendingPacket();
            var channel = packetSendingData.channel();
            var serializedPacket = GSON.toJson(packet, packet.getClass());

            var redisTopic = redissonClient.getTopic(channel);
            redisTopic.publish(serializedPacket);
        });
    }
}