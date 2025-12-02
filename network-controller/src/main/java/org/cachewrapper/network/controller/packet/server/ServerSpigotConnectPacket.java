package org.cachewrapper.network.controller.packet.server;

import org.cachewrapper.network.controller.server.type.ServerType;

import java.net.InetSocketAddress;

public record ServerSpigotConnectPacket(
        String identifier,
        Class<? extends ServerType> serverType,
        InetSocketAddress address,
        int maxPlayers
) {}