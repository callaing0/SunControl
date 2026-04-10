package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.generic.BaseTimeProvider;
import com.suncontrol.core.constant.generic.DistrictProvider;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.entity.log.WeatherLog;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class WeatherLogDto implements BaseTimeProvider, DistrictProvider {

    /// List<WeatherLogDto>를 이용하여 DB저장
    /// Map<D.Code,Map<L.D.T,WeatherLogDto>>를 이용하여 발전데이터 생성
    private District district;
    private LocalDateTime baseTime;
    private ReportDataType dataType;

    private double temperature;
    private int cloudLow;
    private int cloudMid;
    private int cloudHigh;
    private int ghi;
    private Integer weatherCode;
    private Weather weather;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public WeatherLogDto(WeatherLog entity) {
        this.district = entity.getDistrict();
        this.baseTime = entity.getBaseTime();
        this.temperature = entity.getTemperature();
        this.cloudLow = entity.getCloudLow();
        this.cloudMid = entity.getCloudMid();
        this.cloudHigh = entity.getCloudHigh();
        this.weatherCode = entity.getWeatherCode();
        this.weather = Weather.fromCode(weatherCode);
        this.ghi = entity.getGhi();
        this.dataType = ReportDataType.findByDayOffset(entity.getDayOffset());
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    public Integer getDayOffset() {
        return dataType != null ? this.dataType.getDayOffset() : ReportDataType.UNKNOWN.getDayOffset();
    }
}
