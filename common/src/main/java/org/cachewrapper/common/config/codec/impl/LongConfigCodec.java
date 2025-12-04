package org.cachewrapper.common.config.codec.impl;

import org.cachewrapper.common.config.codec.ConfigCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LongConfigCodec implements ConfigCodec<Long, Object> {

    @Override
    public @NotNull Long parse(@NotNull Object serialized) {
        return (Long) serialized;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull List<Long> parseList(@NotNull Object serialized) {
        return (List<Long>) serialized;
    }
}
