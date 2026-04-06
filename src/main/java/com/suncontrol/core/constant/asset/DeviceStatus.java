package com.suncontrol.core.constant.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum DeviceStatus {
    // TODO : 여유가 된다면 private static enum DeviceType 구현
    PLANT_DELETED(0, "PLANT"),
    PLANT_NORMAL(1, "PLANT"),
    PLANT_CAUTION(2, "PLANT"),
    PLANT_END(9, "PLANT"),
    INVERTER_DELETED(10, "INVERTER"),
    INVERTER_NORMAL(11, "INVERTER"),
    INVERTER_ERROR(12,  "INVERTER"),
    INVERTER_END(19, "INVERTER");
    /// 발전소(0~9) 인버터(10~19) 등 장비의 상태값
    private final int code;
    private final String category;

    public static List<DeviceStatus> getByCategory(String category) {
        return Arrays.stream(values())
                .filter(v -> v.category.equals(category))
                .collect(Collectors.toList());
    }

    private static final Map<Integer, DeviceStatus> CODE_MAP =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(
                            DeviceStatus::getCode,
                            Function.identity())));

    public static DeviceStatus fromCode(int code) {
        return CODE_MAP.get(code);
    }
}
