package com.sbq.tools;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.apache.commons.codec.binary.Base64;

/**
 * Kryo序列化辅助类
 * Created by zhangyuan on 2017/2/15.
 */
public class KryoSerializeUtil {

    public static <T> String serializationObject(T obj) {
        Kryo kryo = new Kryo();

        byte[] buffer = new byte[2048];
        Output output = new Output(buffer);
        kryo.writeClassAndObject(output, obj);

        String result = new String(new Base64().encode(output.toBytes()));

        return result;
    }

    public static <T> T deserializationObject(String obj, Class<T> clazz) {

        Kryo kryo = new Kryo();

        Input input = new Input(new Base64().decode(obj));

        T t = (T) kryo.readClassAndObject(input);

        return t;
    }

}
