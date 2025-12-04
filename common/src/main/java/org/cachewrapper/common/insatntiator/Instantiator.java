package org.cachewrapper.common.insatntiator;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Instantiator<T> {

    Map<Class<?>, Object> INSTANTIATED_OBJECTS = new ConcurrentHashMap<>();

    default void instantiate() {
        var instantiatedObject = initialize();
        Preconditions.checkNotNull(instantiatedObject);

        INSTANTIATED_OBJECTS.put(getClass(), instantiatedObject);
    }

    @NotNull
    T initialize();

    @NotNull
    @SuppressWarnings("unchecked")
    default T get() {
        T instantiatedObject = (T) INSTANTIATED_OBJECTS.get(getClass());
        if (instantiatedObject == null) {
            instantiate();
            return this.get();
        }

        return instantiatedObject;
    }
}