package org.cachewrapper.common.config.reader;

import com.google.inject.Injector;
import org.cachewrapper.common.config.Config;
import org.cachewrapper.common.config.placeholder.ConfigPlaceholder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record ConfigReadOption(
        String path,
        Injector injector,
        Class<? extends Config> configType,
        List<ConfigPlaceholder> placeholders
) {

    @NotNull
    public static ConfigReadOption.Builder builder(final @NotNull Injector injector) {
        return new ConfigReadOption.Builder(injector);
    }

    public static class Builder {

        private final Injector injector;
        private final List<ConfigPlaceholder> placeholders = new ArrayList<>();

        private String path;
        private Class<? extends Config> configType;

        public Builder(@NotNull Injector injector) {
            this.injector = injector;
        }

        @NotNull
        public Builder path(@NotNull String path) {
            this.path = path;
            return this;
        }

        @NotNull
        public Builder config(@NotNull Class<? extends Config> configType) {
            this.configType = configType;
            return this;
        }

        @NotNull
        public Builder placeholder(@NotNull String placeholder, @NotNull Object value) {
            var configPlaceholder = new ConfigPlaceholder(placeholder, value);
            placeholders.add(configPlaceholder);

            return this;
        }

        @NotNull
        public <T> T get(Class<T> returnType) {
            return injector.getInstance(ConfigReader.class).get(returnType, build());
        }

        @NotNull
        public <T> List<T> getAsList(Class<T> returnType) {
            return injector.getInstance(ConfigReader.class).getAsList(returnType, build());
        }

        @NotNull
        public ConfigReadOption build() {
            return new ConfigReadOption(path, injector, configType, placeholders);
        }
    }
}
