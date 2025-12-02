package org.cachewrapper.network.controller.server.picker.impl;

import org.cachewrapper.network.controller.server.Server;
import org.cachewrapper.network.controller.server.picker.ServerPicker;

import java.util.function.Predicate;

public class LeastPlayersPicker implements ServerPicker {

    @Override
    public Server getServer(Predicate<Server> predicate) {
        return null;
    }
}
