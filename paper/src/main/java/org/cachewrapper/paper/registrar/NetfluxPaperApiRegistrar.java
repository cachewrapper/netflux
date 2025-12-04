package org.cachewrapper.paper.registrar;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.api.paper.Netflux;
import org.cachewrapper.api.paper.provider.NetfluxPaperProvider;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class NetfluxPaperApiRegistrar implements RegistrarSync {

    private final Injector injector;

    @Override
    public void register() {
        var netfluxPaperProvider = injector.getInstance(NetfluxPaperProvider.class);
        Netflux.setProvider(netfluxPaperProvider);
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}