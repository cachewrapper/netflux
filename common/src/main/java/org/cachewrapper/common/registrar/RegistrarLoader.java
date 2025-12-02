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
import java.util.concurrent.CompletableFuture;

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

        var asyncFutures = orderedRegistrars.stream()
                .filter(registrar -> registrar instanceof RegistrarAsync)
                .map(registrar -> ((RegistrarAsync) registrar).register())
                .toArray(CompletableFuture[]::new);

        CompletableFuture<Void> registrarsAsyncChain = CompletableFuture.allOf(asyncFutures);
        registrarsAsyncChain.thenRun(() -> orderedRegistrars.stream()
                .filter(registrar -> registrar instanceof RegistrarSync)
                .forEach(registrar -> ((RegistrarSync) registrar).register())
        );
    }

}