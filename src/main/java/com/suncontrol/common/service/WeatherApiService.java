package com.suncontrol.common.service;

import com.suncontrol.common.dto.api.WeatherResponseDto;
import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.asset.PlantDto;
import com.suncontrol.core.dto.asset.PlantWeatherApiDto;
import com.suncontrol.core.dto.log.WeatherLogDto;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.service.log.DailyWeatherService;
import com.suncontrol.core.service.log.RadiationLogService;
import com.suncontrol.core.service.log.WeatherLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherApiService {
    /// 기상 조회 API 서비스

    private final WeatherLogService weatherLogService;
    private final RadiationLogService radiationLogService;
    private final DailyWeatherService dailyWeatherService;
    private final PlantService plantService;

    public void requestAndSaveWeather() {
        List<PlantDto> plants = plantService.findAllActive();
        List<PlantWeatherApiDto> plantWeatherApiList =
                plants.stream().map(PlantWeatherApiDto::new).toList();
        /// 기상 정보 요청해서 각 서비스의 필요정보 추출하여 저장
        List<WeatherResponseDto> responses = getWeatherResponses(plantWeatherApiList);

        saveWeatherResponses(responses);
    }

    private List<WeatherResponseDto> getWeatherResponses(List<PlantWeatherApiDto> plants) {
        return Collections.emptyList();
    }

    @Transactional
    public void saveWeatherResponses(List<WeatherResponseDto> responses) {
        if(responses.isEmpty()) return;
        /// 응답객체 리스트를 각각의 리스트로 변환하여 저장
        weatherLogService.saveAll(
                responses.stream().flatMap(
                        res -> res.getWeatherLogs()
                                .stream()).toList());
        radiationLogService.saveAll(
                responses.stream().flatMap(
                        res -> res.getRadiationLogs()
                                .stream()).toList());
        dailyWeatherService.saveAll(
                responses.stream().flatMap(
                        res -> res.getDailyWeathers()
                                .stream()).toList());
    }
}
