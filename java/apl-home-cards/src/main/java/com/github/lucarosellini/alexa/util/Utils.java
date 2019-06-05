package com.github.lucarosellini.alexa.util;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static final Logger logger = LogManager.getLogger(Utils.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Field getField(Class clazz, String fieldName) throws NoSuchFieldException {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();
            if (superClass == null) {
                throw e;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }

    public static void makePublic(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    public static boolean supportsAPL(HandlerInput handlerInput) {
        try {
            return handlerInput.getRequestEnvelope().getContext().getSystem().getDevice()
                    .getSupportedInterfaces().getAlexaPresentationAPL() != null;
        } catch (Exception e) {
            logger.error("Cannot detect APL", e);
            return false;
        }
    }

    public static Map<String, Object> resourceToMap(String fileName) throws URISyntaxException, IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                OBJECT_MAPPER.getClass().getResourceAsStream("/" + fileName)
        ))) {
            return OBJECT_MAPPER.readValue(reader, new TypeReference<HashMap<String, Object>>() {
            });
        }
    }
}
