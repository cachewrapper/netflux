package org.cachewrapper.paper.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class ServerHeartbeat implements Runnable {
    @Override
    public void run() {

    }
}
