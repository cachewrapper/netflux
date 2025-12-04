package org.cachewrapper.common.config;

import com.google.inject.Injector;
import org.cachewrapper.common.config.accessor.ConfigAccessor;
import org.cachewrapper.common.config.reader.ConfigReadOption;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class ConfigProvider {

    @NotNull
    public static <T extends Config> ConfigReadOption.Builder get(
            @NotNull Class<T> configType,
            @NotNull Injector injector
    ) {
        return ConfigReadOption.builder(injector).config(configType);
    }

    public static void load(
            @NotNull Class<? extends Config> configType,
            @NotNull Injector injector
    ) {
        var config = injector.getInstance(configType);
        var configAccessor = injector.getInstance(ConfigAccessor.class);

        configAccessor.load(config);
    }

    public static void loadAll(
            @NotNull Collection<Class<? extends Config>> types,
            @NotNull Injector injector
    ) {
        var configAccessor = injector.getInstance(ConfigAccessor.class);
        configAccessor.loadAll(types, injector);
    }
}
