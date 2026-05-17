package com.dibimbingqa.utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDataLoader {
    public static String load(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get("src/test/resources/" + path)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load file: " + path, e);
        }

    }
}
