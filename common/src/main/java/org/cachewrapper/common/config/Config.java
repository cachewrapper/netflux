package org.cachewrapper.common.config;

import org.jetbrains.annotations.NotNull;

public interface Config {

    @NotNull
    ConfigMetadata metadata();
}