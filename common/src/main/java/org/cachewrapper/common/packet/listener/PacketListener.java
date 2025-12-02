package org.cachewrapper.common.packet.listener;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPubSub;

public abstract class PacketListener<T> extends JedisPubSub {

    private final Gson GSON = new Gson();

    @NotNull
    public abstract Class<T> getPacketType();

    @NotNull
    public abstract String getChannel();

    public abstract void onPacket(@NotNull T packet);

    @Override
    public final void onMessage(@NotNull String channel, @NotNull String message) {
        if (!channel.equals(getChannel())) return;

        T parsedPacket = GSON.fromJson(message, getPacketType());
        onPacket(parsedPacket);
    }
}