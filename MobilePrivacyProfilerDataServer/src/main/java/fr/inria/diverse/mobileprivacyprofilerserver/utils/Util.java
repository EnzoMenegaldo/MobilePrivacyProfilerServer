package fr.inria.diverse.mobileprivacyprofilerserver.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Util {

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public static byte[] stringToByte(String string) throws UnsupportedEncodingException {
        return string.getBytes(UTF8_CHARSET);
    }

    public static String byteToString(byte[] bytes){
        return new String(bytes, UTF8_CHARSET);
    }
}
