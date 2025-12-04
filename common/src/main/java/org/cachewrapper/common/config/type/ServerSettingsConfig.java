package org.cachewrapper.common.config.type;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.config.Config;
import org.cachewrapper.common.config.ConfigMetadata;
import org.cachewrapper.common.config.loader.impl.ResourceConfigLoader;
import org.cachewrapper.common.config.parser.impl.TomlConfigParser;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerSettingsConfig implements Config {

    private final Injector injector;

    @Override
    public @NotNull ConfigMetadata metadata() {
        return ConfigMetadata.builder()
                .name("server-settings.toml")
                .parser(injector.getInstance(TomlConfigParser.class))
                .loader(injector.getInstance(ResourceConfigLoader.class))
                .build();
    }
}