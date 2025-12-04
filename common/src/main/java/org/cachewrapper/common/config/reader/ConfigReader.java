package org.cachewrapper.common.config.reader;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.config.ConfigMetadata;
import org.cachewrapper.common.config.accessor.ConfigAccessor;
import org.cachewrapper.common.config.parser.ConfigParser;
import org.cachewrapper.common.config.parser.ParsedConfig;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ConfigReader {

    private final ConfigAccessor configAccessor;
    private final Injector injector;

    @NotNull
    @SuppressWarnings("unchecked")
    public <R, U> R get(@NotNull Class<R> returnType, @NotNull ConfigReadOption readOption) {
        var config = injector.getInstance(readOption.configType());

        ParsedConfig<U> parsedConfig = configAccessor.get(readOption);
        var metadata = config.metadata();

        ConfigParser<U> parser = (ConfigParser<U>) metadata.parser();
        var line = parser.get(readOption.path(), parsedConfig, returnType);

        if (returnType == String.class) {
            line = (R) applyPlaceholder((String) line, readOption);
        }

        return line;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <R, U> List<R> getAsList(@NotNull Class<R> returnType, @NotNull ConfigReadOption readOption) {
        var config = injector.getInstance(readOption.configType());

        ParsedConfig<U> parsedConfig = configAccessor.get(readOption);
        ConfigMetadata metadata = config.metadata();

        ConfigParser<U> parser = (ConfigParser<U>) metadata.parser();
        List<R> list = parser.getAsList(readOption.path(), parsedConfig, returnType);

        if (returnType == String.class) {
            list = (List<R>) list.stream()
                    .map(line -> applyPlaceholder((String) line, readOption))
                    .toList();
        }

        return list;
    }

    @NotNull
    private String applyPlaceholder(@NotNull String line, @NotNull ConfigReadOption readOption) {
        var result = line;
        for (var placeholder : readOption.placeholders()) {
            var placeholderName = placeholder.placeholder();
            if (placeholderName.charAt(0) != '%') {
                placeholderName = "%" + placeholderName + "%";
            }

            result = line.replace(placeholderName, String.valueOf(placeholder.value()));
        }
        return result;
    }
}