package org.cachewrapper.velocity.registrar;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.api.velocity.Netflux;
import org.cachewrapper.api.velocity.provider.NetfluxVelocityProvider;
import org.cachewrapper.common.registrar.RegistrarPriority;
import org.cachewrapper.common.registrar.type.RegistrarSync;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class NetfluxVelocityApiRegistrar implements RegistrarSync {

    private final Injector injector;

    @Override
    public void register() {
        var netfluxVelocityProvider = injector.getInstance(NetfluxVelocityProvider.class);
        Netflux.setProvider(netfluxVelocityProvider);
    }

    @Override
    public RegistrarPriority getPriority() {
        return RegistrarPriority.FIRST;
    }
}
