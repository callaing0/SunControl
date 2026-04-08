package com.suncontrol.common.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.suncontrol.core.constant.common.Weather;
import com.suncontrol.core.constant.util.ReportDataType;
import com.suncontrol.core.dto.asset.PlantWeatherApiDto;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.dto.log.WeatherLogDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
public class WeatherResponseDto {
    /// 기상 API 수신용 Dto

    @JsonProperty("latitude")
    private BigDecimal latitude;
    @JsonProperty("longitude")
    private BigDecimal longitude;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("hourly") //TODO 객체의 리스트가 아니라 리스트를 필드로 가진 객체임
    private WeatherHourlyResponseDto hourly;
    @JsonProperty("daily")
    private DailyWeatherResponseDto daily;
    @JsonIgnore
    private PlantWeatherApiDto plant; /// API 수신 후 발전소 정보 외부에서 주입

    @JsonIgnore
    private final LocalDateTime responseTime = LocalDateTime.now();
    /// 각 날씨 컬럼의 created_at이나 updated_at을 강제로 이것으로 주입하기 위함

    @JsonIgnore
    private Integer getDayOffset(LocalDateTime targetTime) {
        /// 들어오는 baseTime 이 responseTime 보다 미래시점 -> dayOffset > 0
        /// 들어오는 baseTime 이 responseTime 보다 과거시점 -> "실측"으로 처리
        return Math.max(targetTime.compareTo(responseTime), 0);
    }

    @JsonIgnore
    private ReportDataType getReportDataType(Integer dayOffset) {
        return ReportDataType.findByDayOffset(dayOffset);
    }

    @JsonIgnore
    private ReportDataType getReportDataType(LocalDateTime targetTime) {
        return getReportDataType(getDayOffset(targetTime));
    }

    @JsonIgnore
    public List<WeatherLogDto> getWeatherLogs() {
        List<LocalDateTime> timeList = hourly.getBaseTime();
        int size = timeList.size();
        List<WeatherLogDto> weatherLogs = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            /// 빌더 삭제하고 차근차근 꺼내서 정리하기
            WeatherLogDto dto = new WeatherLogDto();

            /// hourly.getBaseTime().get(i)) 를 사용해도 되지만
            /// 이미 꺼내둔 객체를 이용하는 게 리소스 낭비를 줄이는 법
            dto.setDistrict(plant.getDistrict());
            dto.setBaseTime(timeList.get(i));
            dto.setDataType(getReportDataType(timeList.get(i)));

            /// 다음은 UK가 아닌 파트들
            dto.setTemperature(hourly.getTemperature().get(i));
            dto.setCloudLow(hourly.getCloudLow().get(i));
            dto.setCloudMid(hourly.getCloudMid().get(i));
            dto.setCloudHigh(hourly.getCloudHigh().get(i));
            dto.setGhi(hourly.getGhi().get(i));
            dto.setWeatherCode(hourly.getWeatherCode().get(i));
            dto.setCreatedAt(responseTime);
            dto.setUpdatedAt(responseTime);
            weatherLogs.add(dto);
        }

        return weatherLogs;
    }

    @JsonIgnore
    public List<RadiationLogDto> getRadiationLogs() {
        List<LocalDateTime> timeList = hourly.getBaseTime();
        int size = timeList.size();
        List<RadiationLogDto> radiationLogs = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            RadiationLogDto dto = new RadiationLogDto();

            /// UK 파트
            dto.setPlantId(plant.getId());
            dto.setBaseTime(timeList.get(i));
            dto.setDataType(getReportDataType(timeList.get(i)));

            /// UK 아닌 파트
            dto.setGti(hourly.getGti().get(i));
            dto.setGtiInstant(hourly.getGtiInstant().get(i));
            dto.setCreatedAt(responseTime);
            dto.setUpdatedAt(responseTime);
            radiationLogs.add(dto);
        }

        return radiationLogs;
    }

    @JsonIgnore
    public List<DailyWeatherDto> getDailyWeathers() {
        List<LocalDate> dateList = daily.getBaseDate();
        int size = dateList.size();
        List<DailyWeatherDto> dailyWeathers = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            DailyWeatherDto dto = new DailyWeatherDto();

            /// UK 파트
            dto.setDistrict(plant.getDistrict());
            dto.setBaseDate(dateList.get(i));
            /// 당일의 예보는 0시 0분으로 해둬야 "당일실측" 을 강제할 수 있다.
            dto.setDataType(getReportDataType(dateList.get(i).atStartOfDay()));

            /// UK 아닌 파트
            dto.setTempMax(daily.getTempMax().get(i));
            dto.setTempMin(daily.getTempMin().get(i));
            dto.setWeatherCode(daily.getWeatherCode().get(i));
            dto.setSunrise(daily.getSunrise().get(i));
            dto.setSunset(daily.getSunset().get(i));
            dto.setSunshineDuration(daily.getSunshineDuration().get(i));
            dto.setDaylightDuration(daily.getDaylightDuration().get(i));
            dto.setPrecSum(daily.getPrecSum().get(i));
            dto.setSnowSum(daily.getSnowSum().get(i));
            dto.setCreatedAt(responseTime);
            dto.setUpdatedAt(responseTime);
            dailyWeathers.add(dto);
        }

        return dailyWeathers;
    }
}
