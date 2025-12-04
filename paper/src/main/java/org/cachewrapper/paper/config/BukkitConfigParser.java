package org.cachewrapper.paper.config;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.configuration.file.YamlConfiguration;
import org.cachewrapper.common.config.codec.impl.ArrayListConfigCodec;
import org.cachewrapper.common.config.codec.impl.IntegerConfigCodec;
import org.cachewrapper.common.config.codec.impl.LongConfigCodec;
import org.cachewrapper.common.config.codec.impl.StringConfigCodec;
import org.cachewrapper.common.config.parser.ConfigParser;
import org.cachewrapper.common.config.parser.ParsedConfig;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class BukkitConfigParser extends ConfigParser<YamlConfiguration> {

    @Inject
    public BukkitConfigParser() {
        super(Map.of(
                String.class, new StringConfigCodec(),
                Integer.class, new IntegerConfigCodec(),
                ArrayList.class, new ArrayListConfigCodec(),
                List.class, new ArrayListConfigCodec(),
                Long.class, new LongConfigCodec()
        ));
    }

    @Override
    public @NotNull ParsedConfig<YamlConfiguration> parse(@NotNull Reader reader) {
        return new ParsedConfig<>(YamlConfiguration.loadConfiguration(reader), YamlConfiguration.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <U> @NotNull U getConfigData(@NotNull String path, @NotNull ParsedConfig<YamlConfiguration> parsedConfig) {
        YamlConfiguration data = parsedConfig.data();
        Object configData = data.get(path);

        Preconditions.checkNotNull(configData, "Couldn't find any config data by path %s".formatted(path));
        return (U) configData;
    }
}