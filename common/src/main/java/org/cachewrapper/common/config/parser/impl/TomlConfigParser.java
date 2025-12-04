package org.cachewrapper.common.config.parser.impl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.moandjiezana.toml.Toml;
import lombok.SneakyThrows;
import org.cachewrapper.common.config.codec.impl.ArrayListConfigCodec;
import org.cachewrapper.common.config.codec.impl.IntegerConfigCodec;
import org.cachewrapper.common.config.codec.impl.LongConfigCodec;
import org.cachewrapper.common.config.codec.impl.StringConfigCodec;
import org.cachewrapper.common.config.parser.ConfigParser;
import org.cachewrapper.common.config.parser.ParsedConfig;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class TomlConfigParser extends ConfigParser<Toml> {

    @Inject
    public TomlConfigParser() {
        super(Map.of(
                String.class, new StringConfigCodec(),
                Integer.class, new IntegerConfigCodec(),
                ArrayList.class, new ArrayListConfigCodec(),
                List.class, new ArrayListConfigCodec(),
                Long.class, new LongConfigCodec()
        ));
    }

    @Override
    public @NotNull ParsedConfig<Toml> parse(@NotNull Reader reader) {
        var toml = new Toml().read(reader);
        return new ParsedConfig<>(toml, Toml.class);
    }

    @SneakyThrows
    @Override
    @SuppressWarnings("unchecked")
    protected <U> @NotNull U getConfigData(@NotNull String path, @NotNull ParsedConfig<Toml> parsedConfig) {
        var toml = parsedConfig.data();

        MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Toml.class, MethodHandles.lookup());
        MethodHandle methodHandle = lookup.findVirtual(Toml.class, "get", MethodType.methodType(Object.class, String.class));

        U value = (U) methodHandle.invoke(toml, path);
        Preconditions.checkNotNull(value, "Value must not be null: " + path);

        return value;
    }
}
