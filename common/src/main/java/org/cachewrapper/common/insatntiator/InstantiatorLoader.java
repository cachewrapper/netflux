package org.cachewrapper.common.insatntiator;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.cachewrapper.common.insatntiator.impl.RedisInstantiator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class InstantiatorLoader {

    private static final List<Class<? extends Instantiator<?>>> DEFAULT_INSTANTIATORS = new ArrayList<>(List.of(
            RedisInstantiator.class
    ));

    private final Injector injector;

    public void loadInstantiators(
            @NotNull Collection<Class<? extends Instantiator<?>>> instantiatorClasses
    ) {
        instantiatorClasses.addAll(DEFAULT_INSTANTIATORS);
        instantiatorClasses.stream()
                .map(injector::getInstance)
                .forEach(Instantiator::instantiate);
    }
}