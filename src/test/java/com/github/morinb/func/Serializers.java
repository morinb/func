package com.github.morinb.func;

import java.io.*;

public final class Serializers
{
    private Serializers()
    {

    }


    public static byte[] serialize(Object obj)
    {
        try (ByteArrayOutputStream buf = new ByteArrayOutputStream();
             ObjectOutputStream stream = new ObjectOutputStream(buf)) {
            stream.writeObject(obj);
            return buf.toByteArray();
        } catch (IOException x) {
            throw new IllegalStateException("Error serializing object", x);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] objectData)
    {
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(objectData))) {
            return (T) stream.readObject();
        } catch (IOException | ClassNotFoundException x) {
            throw new IllegalStateException("Error deserializing object", x);
        }
    }

}
