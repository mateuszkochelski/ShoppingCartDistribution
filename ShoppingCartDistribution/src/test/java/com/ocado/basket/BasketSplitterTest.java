package com.ocado.basket;


import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketSplitterTest {


   @Test
    void testIsMaxPackageCourierSelected() throws IOException {
        //given
        String configContent = "{" +
               "\"Carrots (1kg)\": [\"Express Delivery\", \"Click&Collect\"]," +
               "\"Cold Beer (330ml)\": [\"Express Delivery\"]," +
               "\"Steak (300g)\": [\"Express Delivery\", \"Click&Collect\"]," +
               "\"AA Battery (4 Pcs.)\": [\"Express Delivery\", \"Courier\"]," +
               "\"Espresso Machine\": [\"Express Delivery\", \"Click&Collect\"]," +
               "\"Garden Chair\": [\"Express Delivery\"]" +
               "}";
        Path tempConfigFile = Files.createTempFile("testConfig", ".json");
        Files.write(tempConfigFile, configContent.getBytes());
        LinkedList<String> basket = new LinkedList<>(Arrays.asList("Carrots (1kg)", "Cold Beer (330ml)"));
        //when
        BasketSplitter basketSplitter = new BasketSplitter(tempConfigFile.toString());
        var splitBasket = basketSplitter.split(basket);
        //then
        assertEquals(countMaximumSizeOfPackage(splitBasket),2);
    }
    @Test
    void testIsEmptyListReturnsZeroCouriers() throws IOException {
        //given
        String configContent = "{" +
                "\"Carrots (1kg)\": [\"Express Delivery\", \"Click&Collect\"]"+
                "}";
        Path tempConfigFile = Files.createTempFile("testConfig", ".json");
        Files.write(tempConfigFile, configContent.getBytes());
        LinkedList<String> basket = new LinkedList<>();
        //when
        BasketSplitter basketSplitter = new BasketSplitter(tempConfigFile.toString());
        var splitBasket = basketSplitter.split(basket);
        //then
        assertEquals(countCouriers(splitBasket),0);
    }

    private int countCouriers(Map<String, List<String>> splitBasket)
    {
        return splitBasket.size();
    }
    private int countMaximumSizeOfPackage(Map<String, List<String>> splitBasket)
    {
        return splitBasket.values().stream().mapToInt(List::size).max().orElse(0);
    }
}