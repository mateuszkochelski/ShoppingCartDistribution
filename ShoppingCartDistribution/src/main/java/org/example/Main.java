package org.example;

import com.ocado.basket.BasketSplitter;

import java.util.Arrays;
import java.util.LinkedList;


public class Main {
    public static void main(String[] args) {
        BasketSplitter basketSplitter = new BasketSplitter(" ");
        LinkedList<String> linkedList = new LinkedList<>(Arrays.asList("Cocoa Butter", "Tart - Raisin And Pecan", "Table Cloth 54x72 White", "Flower - Daisies", "Fond - Chocolate", "Cookies - Englishbay Wht"));
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