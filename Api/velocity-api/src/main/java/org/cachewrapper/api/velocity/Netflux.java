package org.cachewrapper.api.velocity;

import org.cachewrapper.api.velocity.provider.NetfluxVelocityProvider;

public class Netflux {

    private static volatile NetfluxVelocityProvider netfluxVelocityProvider;

    public static NetfluxVelocityProvider get() {
        return netfluxVelocityProvider;
    }

    public static void setProvider(NetfluxVelocityProvider provider) {
        netfluxVelocityProvider = provider;
    }
}
