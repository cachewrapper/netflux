package org.cachewrapper.common.config.codec.impl;

import org.cachewrapper.common.config.codec.ConfigCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IntegerConfigCodec implements ConfigCodec<Integer, Object> {

    @Override
    public @NotNull Integer parse(@NotNull Object serialized) {
        return (Integer) serialized;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull List<Integer> parseList(@NotNull Object serialized) {
        return (List<Integer>) serialized;
    }
}