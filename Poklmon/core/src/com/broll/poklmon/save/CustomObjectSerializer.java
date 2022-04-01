package com.broll.poklmon.save;

import com.broll.pokllib.animation.AnimationDex;
import com.broll.pokllib.attack.AttackDex;
import com.broll.pokllib.item.ItemDex;
import com.broll.pokllib.map.MapDex;
import com.broll.pokllib.poklmon.PoklDex;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.minlog.Log;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;

public final class CustomObjectSerializer {

    private CustomObjectSerializer() {

    }

    public static <T> T read(String save, Class<? extends T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        Log.set(Log.LEVEL_WARN);
        try {
            Input input = new Input(new ByteArrayInputStream(save.getBytes()));
            return kryo.readObject(input, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String write(Object object) {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        Log.set(Log.LEVEL_WARN);
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Output output = new Output(stream);
            kryo.writeObject(output, object);
            output.close();
            return stream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
