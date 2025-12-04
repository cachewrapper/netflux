package org.cachewrapper.common.config.accessor;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.config.Config;
import org.cachewrapper.common.config.parser.ParsedConfig;
import org.cachewrapper.common.config.reader.ConfigReadOption;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ConfigAccessor {

    private final Map<Class<? extends Config>, ParsedConfig<?>> parsedConfigs = new ConcurrentHashMap<>();

    @NotNull
    @SuppressWarnings("unchecked")
    public <T> ParsedConfig<T> get(@NotNull ConfigReadOption readOption) {
        var configType = readOption.configType();
        var parsedConfig = (ParsedConfig<T>) parsedConfigs.get(configType);

        Preconditions.checkNotNull(parsedConfig, "Config %s wasn't loaded".formatted(configType));
        return parsedConfig;
    }

    @NotNull
    public <T extends Config> ParsedConfig<?> load(@NotNull T config) {
        var metadata = config.metadata();
        var configName = metadata.name();

        var loader = metadata.loader();
        var loadedConfig = loader.load(configName);

        var parser = metadata.parser();
        var parsedConfig = parser.parse(loadedConfig);

        parsedConfigs.put(config.getClass(), parsedConfig);
        return parsedConfig;
    }

    public void loadAll(
            @NotNull Collection<Class<? extends Config>> types,
            @NotNull Injector injector
    ) {
        var configs = types.stream()
                .map(injector::getInstance)
                .toList();

        configs.forEach(this::load);
    }
}