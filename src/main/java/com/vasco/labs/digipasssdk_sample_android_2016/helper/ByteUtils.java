package com.vasco.labs.digipasssdk_sample_android_2016.helper;

import java.nio.ByteBuffer;

/**
 * Created by ssadi on 07/03/18.
 */
public class ByteUtils {
    private static ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE);

    public static byte[] longToBytes(long x) {
        buffer.clear();
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        long result =  0;
        try {
            buffer.clear();
            buffer.put(bytes, 0, bytes.length);
            buffer.flip();//need flip
            return buffer.getLong();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
