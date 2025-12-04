package org.cachewrapper.network.controller.packet.player;

import java.util.UUID;

public record PlayerSendServerPacket(
        UUID playerUUID,
        String identifier
) {
}
