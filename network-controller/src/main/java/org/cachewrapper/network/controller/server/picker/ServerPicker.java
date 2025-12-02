package org.cachewrapper.network.controller.server.picker;

import org.cachewrapper.network.controller.server.Server;

import java.util.function.Predicate;

public interface ServerPicker {
    Server getServer(Predicate<Server> predicate);
}