package com.ocado.basket;

import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasketSplitter {

    private final List<Set<String>> couriersProductsMap;

    private final List<String> couriersNames;

    private final ArrayList<String> allPossibilitiesArray;

    public BasketSplitter(String absolutePathToConfigFile)  {
        try {
            ParsedData parsedData = JsonParser.parseConfigFile(absolutePathToConfigFile);
            this.couriersProductsMap = parsedData.couriersProductsMap();
            this.couriersNames = parsedData.couriersNames();
        }
        catch (IOException e) {
            System.out.println("COULD NOT FIND CONFIG FILE!");
            throw new RuntimeException(e);
        }
        allPossibilitiesArray = initializeAllPossibilitiesArray(couriersProductsMap.size());
    }

    public Map<String, List<String>> split(List<String> items){
        int finalCouriersNeeded = 0;
        int finalMaxCourierPackage = 0;
        Map<String, List<String>> finalPackageDistribution = new HashMap<>();

        for (String bitMask : allPossibilitiesArray)
        {
            int couriersNeeded = Math.toIntExact(bitMask.chars().filter(c->c =='1').count());
            if(finalCouriersNeeded != 0 && couriersNeeded > finalCouriersNeeded)
                break;
            Map<String, List<String>> localDistribution = distributePackages(bitMask, items).orElseGet(HashMap::new);
            int maxCourierPackage = localDistribution.values().stream().mapToInt(List::size).max().orElse(0);
            if(maxCourierPackage > finalMaxCourierPackage)
            {
                finalPackageDistribution = localDistribution;
                finalMaxCourierPackage = maxCourierPackage;
                finalCouriersNeeded = couriersNeeded;
            }
        }
        return finalPackageDistribution;
    }

    private Optional<Map<String, List<String>>> distributePackages(String bitMask, List<String> items) {
        Map<String, List<String>> result = new HashMap<>();
        HashSet<String> expectedSet = new HashSet<>(items);

        if(!isDistributionPossible(expectedSet, bitMask))
            return Optional.empty();
        int maxCourierSetIndex = maxCourierSetIndex(expectedSet, bitMask);

        List<String> productsToAdd = getProductsToAdd(expectedSet, new HashSet<>(), maxCourierSetIndex);

        HashSet<String> accumulativeSet = new HashSet<>(productsToAdd);
        result.put(couriersNames.get(maxCourierSetIndex), productsToAdd);

        IntStream.range(0, bitMask.length()).filter(i -> bitMask.charAt(i) == '1' && i!=maxCourierSetIndex)
                .forEach(i ->
                {
                    result.put(couriersNames.get(i), getProductsToAdd(expectedSet, accumulativeSet, i));
                    accumulativeSet.addAll(getProductsToAdd(expectedSet, accumulativeSet, i));
                });

        return Optional.of(result);
    }

    private int maxCourierSetIndex(HashSet<String> expectedSet, String bitMask) {
        return IntStream.range(0,bitMask.length())
                .filter(i -> bitMask.charAt(i)=='1')
                .reduce((maxIndex, currentIndex) ->
                        setIntersection(couriersProductsMap.get(currentIndex),expectedSet).size() > setIntersection(couriersProductsMap.get(maxIndex),expectedSet).size()
                                ? currentIndex : maxIndex).orElse(0);
    }

    private boolean isDistributionPossible(HashSet<String> expectedSet, String bitMask) {
        return IntStream.range(0, bitMask.length())
                .filter(i -> bitMask.charAt(i) == '1')
                .mapToObj(couriersProductsMap::get)
                .flatMap(Set::stream)
                .collect(Collectors.toSet())
                .containsAll(expectedSet);
    }

    private ArrayList<String> initializeAllPossibilitiesArray(int size)
    {
        ArrayList<String> allPossibilities = new ArrayList<>(size);

        IntStream.range(1,(int)Math.pow(2,size)).forEach(i ->
        {
            String subSet = Integer.toBinaryString(i);
            allPossibilities.add(Stream.concat(Collections.nCopies(size - subSet.length(),"0").stream(),Stream.of(subSet)).collect(Collectors.joining()));
        });

        return allPossibilities;
    }
    private HashSet<String> setIntersection(Set<String> A, Set<String> B)
    {
        return A.stream().filter(B::contains).collect(Collectors.toCollection(HashSet::new));
    }
    private List<String> getProductsToAdd(Set<String> expectedSet, Set<String> accumulativeSet, int addedSetIndex) {
        return couriersProductsMap.get(addedSetIndex).stream()
                .filter(item -> !accumulativeSet.contains(item) && expectedSet.contains(item))
                .collect(Collectors.toList());
    }
}