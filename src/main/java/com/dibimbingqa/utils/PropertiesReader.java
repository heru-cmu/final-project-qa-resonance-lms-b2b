package com.dibimbingqa.utils;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = PropertiesReader.class
                .getClassLoader()
                .getResourceAsStream("data.properties")) {
            if (input == null) {
                throw new RuntimeException("data.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read properties file", e);
        }
    }

    public static String readKey(String key) {
        return properties.getProperty(key);
    }
}
