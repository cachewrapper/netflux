package org.cachewrapper.velocity.registrar;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.config.ConfigProvider;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.cachewrapper.velocity.NetfluxVelocity;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ConfigRegistrar implements RegistrarSync {

    private final Injector injector;
    private final NetfluxVelocity plugin;

    @Override
    public void register() {
        var configs = plugin.getConfigs();
        ConfigProvider.loadAll(configs, injector);
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}