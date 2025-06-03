package com.owsb.poms.system.functions;

import java.util.*;
import java.util.function.*;

public class DataHandler {
    // A Set automatically rejects duplicates
    public static <T, R> List<R> extractUniqueField(List<T> list, Function<T, R> extractor) {
        Set<R> uniqueSet = new LinkedHashSet<>();

        for (T element : list) {
            uniqueSet.add(extractor.apply(element));
        }

        return new ArrayList<>(uniqueSet);
    }
    
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


}
