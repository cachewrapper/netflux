package org.cachewrapper.network.controller.packet.server;

import java.net.InetSocketAddress;

public record ServerSpigotPingPacket(
        String identifier,
        String serverType,
        InetSocketAddress address,
        int maxOnline
) {
}