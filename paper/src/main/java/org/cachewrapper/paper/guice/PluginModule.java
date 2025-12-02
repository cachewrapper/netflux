package org.cachewrapper.paper.guice;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.paper.NetfluxPaper;

@RequiredArgsConstructor
public class PluginModule extends AbstractModule {

    private final NetfluxPaper plugin;

    @Override
    protected void configure() {
        bind(NetfluxPaper.class).toInstance(plugin);
    }
}