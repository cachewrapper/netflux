package org.cachewrapper.common.registrar.type;

import org.cachewrapper.common.registrar.Registrar;

import java.util.concurrent.CompletableFuture;

public interface RegistrarAsync extends Registrar {
    CompletableFuture<Void> register();
}