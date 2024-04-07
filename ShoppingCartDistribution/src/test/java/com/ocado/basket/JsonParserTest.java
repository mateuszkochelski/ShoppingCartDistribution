package com.ocado.basket;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.ocado.basket.JsonParser.parseConfigFile;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonParserTest {

    @Test
    void isInvalidPathThrowsException() {
        String invalidPath = "invalidPath";
        assertThrows(IOException.class, () -> parseConfigFile(invalidPath));
    }

    @Test
    void isCorrectPathDoesNotThrowException()
    {
        String invalidPath = "C:\\Users\\mkoch\\Downloads\\Zadanie\\Zadanie\\config.json";
        assertDoesNotThrow(() -> parseConfigFile(invalidPath));
    }

}