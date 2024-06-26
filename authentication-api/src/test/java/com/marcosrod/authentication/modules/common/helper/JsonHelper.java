package com.marcosrod.authentication.modules.common.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
