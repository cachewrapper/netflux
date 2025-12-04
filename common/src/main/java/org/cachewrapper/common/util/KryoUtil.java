package org.cachewrapper.common.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@UtilityClass
public final class KryoUtil {

    private static final Kryo KRYO = new Kryo();

    public byte[] serialize(Object object) {
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Output output = new Output(byteArrayOutputStream)
        ) {
            KRYO.writeClassAndObject(output, object);
            output.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception exception) {
            throw new RuntimeException("Kryo serialization failed", exception);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] bytes) {
        if (bytes == null) return null;

        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                Input input = new Input(byteArrayInputStream)
        ) {
            return (T) KRYO.readClassAndObject(input);
        } catch (Exception exception) {
            throw new RuntimeException("Kryo deserialization failed", exception);
        }
    }
}
