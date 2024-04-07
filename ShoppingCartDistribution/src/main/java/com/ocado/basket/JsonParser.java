package com.ocado.basket;

import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.io.IOException;
public class JsonParser {

    public static ParsedData getCouriersProductsMap(String absolutePathToConfigFile) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Set<String>> couriersProductsHashMap = new HashMap<>();

        JsonNode rootNode = objectMapper.readTree(new File("C:\\Users\\mkoch\\Downloads\\Zadanie\\Zadanie\\config.json"));

        for (Iterator<String> it = rootNode.fieldNames(); it.hasNext(); ) {
            String product = it.next();

            JsonNode deliveryMethodsNode = rootNode.get(product);
            for (JsonNode deliveryMethod : deliveryMethodsNode)
            {
                if(!couriersProductsHashMap.containsKey(String.valueOf(deliveryMethod)))
                    couriersProductsHashMap.put(String.valueOf(deliveryMethod), new HashSet<>());
                couriersProductsHashMap.get(String.valueOf(deliveryMethod)).add(product);
            }
        }



        List<Set<String>> couriersProductsMap = new ArrayList<>(couriersProductsHashMap.size());
        couriersProductsMap.addAll(couriersProductsHashMap.values());

        List<String> couriersNames = new ArrayList<>(couriersProductsHashMap.keySet());

        return new ParsedData(couriersProductsMap, couriersNames);
    }
}
