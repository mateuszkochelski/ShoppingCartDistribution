package com.ocado.basket;

import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasketSplitter {

    private final List<Set<String>> couriersProductsMap;

    private final List<String> couriersNames;

    private final ArrayList<String> allPossibilitiesArray;

    public BasketSplitter(String absolutePathToConfigFile)  {
        try {
            ParsedData parsedData = JsonParser.getCouriersProductsMap(absolutePathToConfigFile);
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
            var couriersNeeded = Math.toIntExact(bitMask.chars().filter(c->c =='1').count());
            if(finalCouriersNeeded != 0 && couriersNeeded > finalCouriersNeeded)
                break;
            var localDistribution = distributePackages(bitMask, items).orElseGet(HashMap::new);
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
        HashSet<String> expectedSet = new HashSet<>(items);
        HashSet<String> newSet = new HashSet<>();
        Map<String, List<String>> result = new HashMap<>();
        int maxCourierPackageIndex = 0;
        int maxCourierPackageCount = 0;

        for (int i = 0; i < bitMask.length();i++)
            if(bitMask.charAt(i) == '1') {
                newSet.addAll(couriersProductsMap.get(i));
                HashSet<String> tempSet = new HashSet<>(couriersProductsMap.get(i));
                tempSet.retainAll(expectedSet);
                if(tempSet.size() > maxCourierPackageCount)
                {
                    maxCourierPackageIndex = i;
                    maxCourierPackageCount = tempSet.size();
                }
            }

        HashSet<String> differenceSet = new HashSet<>(expectedSet);
        differenceSet.removeAll(newSet);
        if(!differenceSet.isEmpty())
            return Optional.empty();

        newSet = new HashSet<>(couriersProductsMap.get(maxCourierPackageIndex));
        newSet.retainAll(expectedSet);

        differenceSet.addAll(newSet);

        result.put(couriersNames.get(maxCourierPackageIndex), new ArrayList<>(newSet));

        for (int i = 0; i < bitMask.length();i++)
            if(bitMask.charAt(i) == '1' && i != maxCourierPackageIndex) {
                newSet = new HashSet<>(couriersProductsMap.get(i));
                newSet.retainAll(expectedSet);
                Set<String> localSet = new HashSet<>(newSet);

                localSet.removeAll(differenceSet);
                result.put(couriersNames.get(i), new ArrayList<>(localSet));
                differenceSet.addAll(newSet);

            }

        return Optional.of(result);
    }

    private ArrayList<String> initializeAllPossibilitiesArray(int size)
    {
        ArrayList<String> allPossibilities = new ArrayList<>(size);
        for(int i = 1; i < Math.pow(2,size); i++)
        {
            String subSet = Integer.toBinaryString(i);
            subSet = Stream.concat(Collections.nCopies(size - subSet.length(),"0").stream(),Stream.of(subSet)).collect(Collectors.joining());
            allPossibilities.add(subSet);
        }
        allPossibilities.sort(Comparator.comparingInt(s -> Math.toIntExact(s.chars().filter(c -> c == '1').count())));

        return allPossibilities;
    }
}
