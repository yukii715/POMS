package com.owsb.poms.system.functions;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class DataHandler {
    // A Set automatically rejects duplicates
    // E.g. Get all non-repeated category from Item Object
    public static <T, R> List<R> extractUniqueField(List<T> list, Function<T, R> extractor) {
        Set<R> uniqueSet = new LinkedHashSet<>();

        for (T element : list) {
            uniqueSet.add(extractor.apply(element));
        }

        return new ArrayList<>(uniqueSet);
    }
    
    // E.g. Get all non-repeated types from a category
    public static <T, R> List<R> filterAndExtractUnique(
        List<T> list,
        Predicate<T> filter,
        Function<T, R> extractor) {

        Set<R> resultSet = new LinkedHashSet<>();

        for (T element : list) {
            if (filter.test(element)) {
                resultSet.add(extractor.apply(element));
            }
        }

        return new ArrayList<>(resultSet);
    }
    
    // Update properties of an object
    public static <T> void updateFieldAndSave(
        List<T> list,
        Predicate<T> matcher,
        Consumer<T> updater,
        Consumer<List<T>> saver) {

        for (T element : list) {
            if (matcher.test(element)) {
                updater.accept(element);
                saver.accept(list);
                break;
            }
        }
    }
    
    // Get a property from another property
    // E.g. Get supplier name by supplier ID
    public static <T, K, V> V getValueByKey(List<T> list,
                                        Function<T, K> keyExtractor,
                                        K keyToMatch,
                                        Function<T, V> valueExtractor) {
        for (T item : list) {
            if (keyExtractor.apply(item).equals(keyToMatch)) {
                return valueExtractor.apply(item);
            }
        }
        return null;
    }
    
    // Return a list if a property is match to another property
    // E.g. Get all items that supplied by a same supplier ID
    public static <T, K> List<T> getValuesByKey(List<T> list,
                                           Function<T, K> keyExtractor,
                                           K keyToMatch) {
        List<T> result = new ArrayList<>();

        for (T item : list) {
            if (keyExtractor.apply(item).equals(keyToMatch)) {
                result.add(item);
            }
        }

        return result;
    }

    // Return a list that match a property from object in another list
    // E.g. Get all corresponding item from prItem to Item 
    public static <K, S, T> List<S> findMatchingByKey(
            List<S> sourceList,                      // the list with full objects (e.g., Item)
            List<T> keyList,                         // the list with references (e.g., PRItem)
            Function<S, K> sourceKeyExtractor,       // how to get the key from S (e.g., Item::getItemID)
            Function<T, K> keyExtractor              // how to get the key from T (e.g., PRItem::getItemID)
    ) {
        // Build map for fast lookup
        Map<K, S> sourceMap = sourceList.stream()
            .collect(Collectors.toMap(sourceKeyExtractor, Function.identity()));

        // Match objects based on keys
        return keyList.stream()
            .map(keyExtractor)
            .map(sourceMap::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
    
    // Get an object by a property
    // E.g. get a User by UID
    public static <K, V> V getByKey(List<V> list, K key, Function<V, K> keyExtractor) { 
        for (V obj : list) {
            if (keyExtractor.apply(obj).equals(key)) {
                return obj;
            }
        }
        return null; 
    }

}
