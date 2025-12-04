package org.cachewrapper.common.packet.listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.cachewrapper.common.gson.GsonClassTypeAdapter;
import org.cachewrapper.common.gson.InetSocketAddressAdapter;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;

public abstract class PacketListener<T> {

    private final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Class.class, new GsonClassTypeAdapter())
            .registerTypeAdapter(InetSocketAddress.class, new InetSocketAddressAdapter())
            .create();

    @NotNull
    public abstract Class<T> getPacketType();

    @NotNull
    public abstract String getChannel();

    public abstract void onPacket(@NotNull T packet);

    public void onPacket(@NotNull String message) {
        T parsedPacket = GSON.fromJson(message, getPacketType());
        onPacket(parsedPacket);
    }
}