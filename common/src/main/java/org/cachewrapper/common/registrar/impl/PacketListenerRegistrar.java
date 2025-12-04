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

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class PacketListenerRegistrar implements RegistrarSync {

    private static final String LOOKUP_PACKAGE = "org.cachewrapper";

    private final RedisInstantiator redisInstantiator;
    private final Injector injector;

    @Override
    public void register() {
        var packetListeners = new Reflections(LOOKUP_PACKAGE)
                .getSubTypesOf(PacketListener.class)
                .stream()
                .map(injector::getInstance)
                .toList();

        packetListeners.forEach(this::subscribePacketListener);
    }

    private void subscribePacketListener(
            @NotNull PacketListener<?> packetListener
    ) {
        var channel = packetListener.getChannel();

        var redissonClient = redisInstantiator.get();
        var redisTopic = redissonClient.getTopic(channel);

        redisTopic.addListener(String.class, (_, message) -> packetListener.onPacket(message));
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}