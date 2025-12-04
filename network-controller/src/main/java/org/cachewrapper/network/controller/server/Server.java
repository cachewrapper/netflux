package org.cachewrapper.network.controller.server;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

@Getter
@Setter
@Builder
public class Server {

    private final String identifier;
    private final String serverType;
    private final InetSocketAddress address;

    private int maxOnline;
}