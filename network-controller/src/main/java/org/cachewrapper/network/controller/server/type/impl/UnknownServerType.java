package org.cachewrapper.network.controller.server.type.impl;

import org.cachewrapper.network.controller.server.type.ServerType;
import org.jetbrains.annotations.NotNull;

public class UnknownServerType implements ServerType {

    @Override
    public @NotNull String identifier() {
        return "unknown";
    }
}