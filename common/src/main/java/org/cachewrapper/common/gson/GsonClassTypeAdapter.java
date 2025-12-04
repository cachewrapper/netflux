package org.cachewrapper.common.gson;

import com.google.gson.*;

import java.lang.reflect.Type;

public class GsonClassTypeAdapter implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

    @Override
    public JsonElement serialize(Class<?> classType, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(classType.getName());
    }

    @Override
    public Class<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Class.forName(json.getAsString());
        } catch (ClassNotFoundException exception) {
            throw new JsonParseException("Cannot deserialize class: " + json.getAsString(), exception);
        }
    }
}
