package com.suncontrol.core.util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataCollectorsUtil {

    public static <T> List<T> nullFilteredList(List<T> list) {
        return nullFilteredStream(list.stream()).collect(Collectors.toList());
    }

    public static <T> Stream<T> nullFilteredStream(Stream<T> stream) {
        return stream.filter(Objects::nonNull);
    }

    public static <K, V, R> Map<K, R> transformValues(
            Map<K, V> sourceMap,
            Function<V, R> valueMapper
    ) {
        return nullFilteredStream(sourceMap.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> valueMapper.apply(entry.getValue()),
                        (existing, replacement) -> replacement
                ));
    }

    public static <K, T> Map<K, T> mapBy
            (List<T> list, Function<T, K> keymap) {
        return mapBy(list, keymap, Function.identity());
    }

    public static <K, T, R> Map<K, R> mapBy
            (List<T> list, Function<T, K> keymap, Function<T, R> valueMapper) {
        return nullFilteredStream(list.stream())
                .collect(Collectors.toMap(
                        keymap,
                        valueMapper,
                        (existing, replacement) -> replacement
                ));
    }

    // List<Generic> list 를 Map<Key, List<Result>> 로
    // 데이터 형 변환과 맵으로 만드는 메서드
    public static <K, T, R> Map<K, List<R>> groupBy
            (List<T> list, Function<T, K> keymap, Function<T, R> valueMapper) {
        return nullFilteredStream(list.stream())
                .collect(
                        Collectors.groupingBy(
                                keymap,
                                Collectors.mapping
                                        (valueMapper, Collectors.toList())
                        )
                );
    }

    public static <K1, K2, T> Map<K1, Map<K2, T>> groupToMap(
            List<T> list,
            Function<T, K1> keymap1,
            Function<T, K2> keymap2) {
        return groupToMap(list, keymap1, keymap2, Function.identity());
    }

    public static <K1, K2, T, R> Map<K1, Map<K2, R>> groupToMap(
            List<T> list,
            Function<T, K1> keymap1,
            Function<T, K2> keymap2,
            Function<T, R> valueMapper) {
        return nullFilteredStream(list.stream())
                .collect(
                        Collectors.groupingBy(
                                keymap1,
                                Collectors.toMap(
                                        keymap2,
                                        valueMapper,
                                        (existing, replacement) -> replacement
                                )
                        )
                );
    }

    public static <K1, K2, T, R> Map<K1, Map<K2, List<R>>> groupToNestedListMap
            (List<T> list,
             Function<T, K1> key1Mapper,
             Function<T, K2> key2Mapper,
             Function<T, R> valueMapper) {
        return nullFilteredStream(list.stream())
                .collect(
                        Collectors.groupingBy(
                                key1Mapper,
                                Collectors.groupingBy(
                                        key2Mapper,
                                        Collectors.mapping(
                                                valueMapper,
                                                Collectors.toList()
                                        )
                                )
                        )
                );
    }

    public static <K1, K2, T> Map<K1, Map<K2, List<T>>> groupToNestedListMap
            (List<T> list,
             Function<T, K1> key1Mapper,
             Function<T, K2> key2Mapper) {
        return groupToNestedListMap(list, key1Mapper, key2Mapper, Function.identity());
    }

    public static <T> List<T> flatMapping(Map<?, ? extends Collection<T>> map) {
        return nullFilteredStream(map.values().stream())
                .flatMap(Collection::stream)
                .toList();
    }

    public static <T, R> List<R> flatMapping(List<T> list, Function<T, Collection<R>> flatMapper ) {
        return nullFilteredStream(list.stream())
                .map(flatMapper)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /// 특정 형태의 리스트를 다른 데이터형의 리스트로 변환하는 메서드
    public static <T, R> List<R> toDataList(
            Collection<T> collection, Function<T, R> mapper) {
        return nullFilteredStream(collection.stream()).map(mapper).toList();
    }
}
