package org.cachewrapper.api.paper;

import org.cachewrapper.api.paper.provider.NetfluxPaperProvider;

public class Netflux {

    private static volatile NetfluxPaperProvider netfluxPaperProvider;

    public static NetfluxPaperProvider get() {
        return netfluxPaperProvider;
    }

    public static void setProvider(NetfluxPaperProvider provider) {
        netfluxPaperProvider = provider;
    }
}
