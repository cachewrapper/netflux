package org.cachewrapper.paper.registrar;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.config.ConfigProvider;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.cachewrapper.paper.NetfluxPaper;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ConfigRegistrar implements RegistrarSync {

    private final NetfluxPaper plugin;
    private final Injector injector;

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