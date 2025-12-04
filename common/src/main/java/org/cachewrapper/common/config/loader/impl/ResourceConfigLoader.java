package org.cachewrapper.common.config.loader.impl;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.cachewrapper.common.config.loader.ConfigLoader;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ResourceConfigLoader implements ConfigLoader {

    @SneakyThrows
    @Override
    public @NotNull Reader load(@NotNull String name) {
        var dataDirectory = Paths.get("plugins/netflux/");

        Files.createDirectories(dataDirectory);
        Path file = dataDirectory.resolve(name);

        if (Files.notExists(file)) {
            try (var inputStream = getClass().getClassLoader().getResourceAsStream(name)) {
                Preconditions.checkNotNull(inputStream, "%s wasn't loaded".formatted(name));
                Files.copy(inputStream, file);
            }
        }

        return Files.newBufferedReader(file, StandardCharsets.UTF_8);
    }
}
