package org.cachewrapper.common.registrar.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cachewrapper.common.insatntiator.impl.RedisInstantiator;
import org.cachewrapper.common.packet.listener.PacketListener;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class PacketListenerRegistrar implements RegistrarSync {

    private static final String LOOKUP_PACKAGE = "org.cachewrapper";
    private static final ExecutorService PACKET_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    private final RedisInstantiator redisInstantiator;
    private final Injector injector;

    @Override
    public void register() {
        var packetListeners = new Reflections(LOOKUP_PACKAGE)
                .getSubTypesOf(PacketListener.class)
                .stream()
                .map(injector::getInstance)
                .toList();

        var jedisPool = redisInstantiator.get();
        packetListeners.forEach(listener -> subscribePacketListener(jedisPool, listener));
    }

    private void subscribePacketListener(
            @NotNull JedisPool jedisPool,
            @NotNull PacketListener<?> packetListener
    ) {
        PACKET_EXECUTOR.submit(() -> {
            try (var jedis = jedisPool.getResource()) {
                jedis.subscribe(packetListener, packetListener.getChannel());
            }
        });
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}