package org.cachewrapper.common.config;

import lombok.Builder;
import org.cachewrapper.common.config.loader.ConfigLoader;
import org.cachewrapper.common.config.parser.ConfigParser;

@Builder
public record ConfigMetadata(
        String name,
        ConfigLoader loader,
        ConfigParser<?> parser
) {}