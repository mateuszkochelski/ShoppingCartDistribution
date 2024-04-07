package com.ocado.basket;

import java.util.List;
import java.util.Set;

public record ParsedData(List<Set<String>> couriersProductsMap, List<String> couriersNames) {
}
