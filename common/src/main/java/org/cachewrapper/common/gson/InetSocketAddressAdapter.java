package org.cachewrapper.common.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.InetSocketAddress;

public class InetSocketAddressAdapter extends TypeAdapter<InetSocketAddress> {

    @Override
    public void write(JsonWriter jsonWriter, InetSocketAddress address) throws IOException {
        jsonWriter.value(address.getHostName() + ":" + address.getPort());
    }

    @Override
    public InetSocketAddress read(JsonReader jsonReader) throws IOException {
        String[] parts = jsonReader.nextString().split(":");

        var hostName = parts[0];
        var port = Integer.parseInt(parts[1]);

        return new InetSocketAddress(hostName, port);
    }
}
