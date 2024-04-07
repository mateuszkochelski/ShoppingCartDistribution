package org.example;

import com.ocado.basket.BasketSplitter;

import java.util.Arrays;
import java.util.LinkedList;


public class Main {
    public static void main(String[] args) {
        BasketSplitter basketSplitter = new BasketSplitter("C:\\Users\\mkoch\\Downloads\\Zadanie\\Zadanie\\config.json");
        //LinkedList<String> linkedList = new LinkedList<>(Arrays.asList("Cocoa Butter", "Tart - Raisin And Pecan", "Table Cloth 54x72 White", "Flower - Daisies", "Fond - Chocolate", "Cookies - Englishbay Wht"));
        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList("Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole", "Haggis", "Mushroom - Porcini Frozen", "Cake - Miini Cheesecake Cherry", "Sauce - Mint", "Longan", "Bag Clear 10 Lb", "Nantucket - Pomegranate Pear", "Puree - Strawberry", "Numi - Assorted Teas", "Apples - Spartan", "Garlic - Peeled", "Cabbage - Nappa", "Bagel - Whole White Sesame", "Tea - Apple Green Tea"));


        var basketSplit = basketSplitter.split(linkedList);

        for (var oneList : basketSplit.keySet())
        {
            System.out.printf("%s:",oneList);
            for (var vals : basketSplit.get(oneList))
            {
                System.out.println(vals);
            }
        }
    }
}