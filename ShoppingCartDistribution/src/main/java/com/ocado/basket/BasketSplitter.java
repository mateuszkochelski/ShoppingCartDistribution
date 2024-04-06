package com.ocado.basket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasketSplitter {

    private final List<Set<String>> couriersProductsMap;

    public BasketSplitter(String absolutePathToConfigFile)  {
        try
        {
            this.couriersProductsMap = JsonParser.parseConfigFile(absolutePathToConfigFile);
        }
        catch (IOException e)
        {
            System.out.println("COULD NOT FIND CONFIG FILE!");
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<String>> split(List<String> items){
        return new HashMap<>();
    }
}
