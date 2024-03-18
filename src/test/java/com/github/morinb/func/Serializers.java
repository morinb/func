/*
 * Copyright 2024 Baptiste MORIN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
