/**
 * Copyright (c) 2015
 * All rights reserved.
 *
 * @Title ConfigurationDecoder.java
 * @Package com.probestar.configuration.codec
 * @author ProbeStar
 * @Email probestar@qq.com
 * @QQ 344137375
 * @date Jul 30, 2015 10:06:18 AM
 * @version V1.0
 * @Description
 */

package com.probestar.configuration.codec;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.probestar.configuration.utils.ConfigurationTracerHelper;

import java.nio.charset.Charset;

public class ConfigurationDecoder {
    private static ConfigurationTracerHelper _tracer = ConfigurationTracerHelper.getInstance(ConfigurationDecoder.class);
    private static Gson _gson = new Gson();

    public static <T> T decode(String json, Class<T> classOfT) {
        if (json.equals("0"))
            return null;
        T data = null;
        try {
            data = _gson.fromJson(json, classOfT);
        } catch (JsonSyntaxException e) {
            _tracer.error("JSon to Object error. JSon String: " + json, e);
        }
        return data;
    }

    public static <T> T decode(byte[] data, Class<T> classOfT) {
        return decode(decode2String(data), classOfT);
    }

    public static String decode2String(byte[] data) {
        return new String(data, Charset.forName("utf-8"));
    }
}
