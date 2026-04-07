package com.suncontrol.core.dto.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.entity.log.WeatherLog;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WeatherLogDto {

    /// Map<DistrictCode,List<WeatherLogDto>>를 이용하여 DB저장
    /// Map<D.Code,Map<L.D.T,WeatherLogDto>>를 이용하여 발전데이터 생성
    private District district;
    private LocalDateTime baseTime;
    private double temperature;
    private int cloudLow;
    private int cloudMid;
    private int cloudHigh;
    private int ghi;
    private ReportDataType dataType;
    private String weatherCode;
    private Weather weather;

    public WeatherLogDto(WeatherLog entity) {
        this.district = entity.getDistrict();
        this.baseTime = entity.getBaseTime();
        this.temperature = entity.getTemperature();
        this.cloudLow = entity.getCloudLow();
        this.cloudMid = entity.getCloudMid();
        this.cloudHigh = entity.getCloudHigh();
        this.ghi = entity.getGhi();
        this.dataType = ReportDataType.findByDayOffset(entity.getDayOffset());
    }

    public Integer getDayOffset() {
        return dataType != null ? this.dataType.getDayOffset() : ReportDataType.UNKNOWN.getDayOffset();
    }
}
