package org.cachewrapper.common.config.codec.impl;

import org.cachewrapper.common.config.codec.ConfigCodec;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ArrayListConfigCodec implements ConfigCodec<ArrayList<?>, Object> {

    @Override
    public @NotNull ArrayList<?> parse(@NotNull Object serialized) {
        return (ArrayList<?>) serialized;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull List<ArrayList<?>> parseList(@NotNull Object serialized) {
        return (ArrayList<ArrayList<?>>) serialized;
    }
}
