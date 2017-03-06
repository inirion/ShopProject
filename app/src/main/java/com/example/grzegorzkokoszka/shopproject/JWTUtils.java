package com.example.grzegorzkokoszka.shopproject;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by Grzegorz Kokoszka on 2017-02-21.
 */

public class JWTUtils {
    private static String[] split;
    public static void decoded(String JWTEncoded) throws Exception {
        try {
            split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
        } catch (UnsupportedEncodingException e) {
            Log.d("JWT_DECODED","error");
        }
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
    public static String getID() throws UnsupportedEncodingException {
        String body = getJson(split[1]);
        String requiredString = body.substring(body.indexOf(":") + 1, body.indexOf(","));
        return requiredString;
    }
}
