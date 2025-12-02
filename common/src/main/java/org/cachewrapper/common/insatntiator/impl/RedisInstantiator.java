package org.cachewrapper.common.insatntiator.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.insatntiator.Instantiator;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.JedisPool;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class RedisInstantiator implements Instantiator<JedisPool> {

    @NotNull
    @Override
    public JedisPool initialize() {
        return new JedisPool();
    }
}