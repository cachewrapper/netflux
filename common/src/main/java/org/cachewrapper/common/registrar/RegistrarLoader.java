package org.cachewrapper.common.registrar;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.registrar.impl.PacketListenerRegistrar;
import org.cachewrapper.common.registrar.type.RegistrarAsync;
import org.cachewrapper.common.registrar.type.RegistrarSync;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class RegistrarLoader {

    private static final Set<Class<? extends Registrar>> DEFAULT_REGISTRARS = new HashSet<>(Set.of(
            PacketListenerRegistrar.class
    ));

    private final Injector injector;

    public void loadRegistrars(
            @NotNull Collection<Class<? extends Registrar>> registrarClasses
    ) {
        registrarClasses.addAll(DEFAULT_REGISTRARS);

        var orderedRegistrars = registrarClasses.stream()
                .map(injector::getInstance)
                .sorted(Comparator.comparingInt(registrar -> registrar.getPriority().ordinal()))
                .toList();

        orderedRegistrars.stream()
                .filter(registrar -> registrar instanceof RegistrarAsync)
                .forEach(registrar -> {
                    var registrarAsync = (RegistrarAsync) registrar;
                    registrarAsync.register();
                });

        orderedRegistrars.stream()
                .filter(registrar -> registrar instanceof RegistrarSync)
                .forEach(registrar -> {
                    var registrarSync = (RegistrarSync) registrar;
                    try {
                        registrarSync.register();
                    } catch (Exception exception) {
                        throw new RuntimeException(exception);
                    }
                });
    }

}