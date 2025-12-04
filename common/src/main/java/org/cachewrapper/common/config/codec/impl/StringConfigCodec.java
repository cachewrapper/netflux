package org.cachewrapper.common.config.codec.impl;

import org.cachewrapper.common.config.codec.ConfigCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StringConfigCodec implements ConfigCodec<String, Object> {

    @Override
    public @NotNull String parse(@NotNull Object serialized) {
        return (String) serialized;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull List<String> parseList(@NotNull Object serialized) {
        return (List<String>) serialized;
    }
}