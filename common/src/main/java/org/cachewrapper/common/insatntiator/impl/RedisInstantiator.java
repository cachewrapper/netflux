package org.cachewrapper.common.insatntiator.impl;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.insatntiator.Instantiator;
import org.jetbrains.annotations.NotNull;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class RedisInstantiator implements Instantiator<RedissonClient> {

    private final Injector injector;

    @NotNull
    @Override
    public RedissonClient initialize() {
//        var serverSettingsReadOption = ConfigProvider.get(ServerSettingsConfig.class, injector);
//
//        var redisHost = serverSettingsReadOption
//                .path("redis.host")
//                .get(String.class);
//
//        long redisPort = serverSettingsReadOption
//                .path("redis.port")
//                .get(Long.class);

        var config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        config.setCodec(new JsonJacksonCodec());

        return Redisson.create();
    }
}