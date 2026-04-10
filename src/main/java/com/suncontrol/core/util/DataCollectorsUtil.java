package com.suncontrol.core.util;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.generic.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataCollectorsUtil {

    public static <T extends DistrictProvider & PlantIdProvider> Map
            <District, List<Long>> groupIdsByDistrict(List<T> list) {
        return groupBy(list, DistrictProvider::getDistrict, PlantIdProvider::getPlantId);
    }

    public static <K, T, R> Map<K, List<R>>
    groupBy(
            List<T> list, Function<T, K> keymap, Function<T, R> valueMapper
    ) {
        return list.stream()
                .collect(
                        Collectors.groupingBy(
                                keymap,
                                Collectors.mapping(valueMapper, Collectors.toList())
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
        return list.stream()
                .collect(
                        Collectors.groupingBy(
                                keymap1,
                                Collectors.toMap(
                                        keymap2,
                                        valueMapper
                                )
                        )
                );
    }

    public static <T, R extends PlantIdProvider> Map<
            Long, List<R>> groupByPlantId(List<T> list, Function<T, R> mapper) {
        return list.stream()
                .map(mapper)
                .collect(
                        Collectors.groupingBy(
                                PlantIdProvider::getPlantId
                        )
                );
    }

    public static <T extends BaseTimeProvider> LocalDateTime getLastGeneratedTime(
            Collection<T> collection, LocalDateTime defaultTime) {
        return collection.stream()
                .map(BaseTimeProvider::getBaseTime)
                .min(LocalDateTime::compareTo)
                .orElse(defaultTime);
    }

    public static <T extends DistrictProvider & BaseTimeProvider> Map<
            District, Map<LocalDateTime, T>> getMapByDistrictAndTime(List<T> list) {
        return list.stream().collect(
                Collectors.groupingBy(
                        T::getDistrict,
                        Collectors.toMap(
                                T::getBaseTime,
                                Function.identity()
                        )
                )
        );
    }

    public static <T extends DistrictProvider & BaseDateProvider> Map<
            District, Map<LocalDate, T>> getMapByDistrictAndDate(List<T> list) {
        return list.stream().collect(
                Collectors.groupingBy(
                        T::getDistrict,
                        Collectors.toMap(
                                T::getBaseDate,
                                Function.identity()
                        )
                )
        );
    }

    public static <T extends PlantIdProvider & BaseTimeProvider> Map<
            Long, Map<LocalDateTime, T>> getMapByPlantIdAndTime(List<T> list) {
        return list.stream().collect(
                Collectors.groupingBy(
                        T::getPlantId,
                        Collectors.toMap(
                                T::getBaseTime,
                                Function.identity()
                        )
                )
        );
    }

    public static <T> List<T> flatMapping(Map<?, ? extends Collection<T>> map) {
        return map.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }

    public static <T, R> List<R> toDtoList(
            Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).toList();
    }
}
