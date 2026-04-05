package com.suncontrol.common.service;

import com.suncontrol.common.dto.api.WeatherResponseDto;
import com.suncontrol.core.dto.asset.PlantWeatherApiDto;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.core.service.log.DailyWeatherService;
import com.suncontrol.core.service.log.RadiationLogService;
import com.suncontrol.core.service.log.WeatherLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherApiService {
    /// 기상 조회 API 서비스

    private final WeatherLogService weatherLogService;
    private final RadiationLogService radiationLogService;
    private final DailyWeatherService dailyWeatherService;
    private final PlantService plantService;

    public void requestAndSaveWeather() {
        List<PlantWeatherApiDto> plants = plantService.findAllForWeatherApi();
        /// 기상 정보 요청해서 각 서비스의 필요정보 추출하여 저장
        List<WeatherResponseDto> responses = getWeatherResponses(plants);

        saveWeatherResponses(responses);
    }

    private List<WeatherResponseDto> getWeatherResponses(List<PlantWeatherApiDto> plants) {
        return null;
    }

    @Transactional
    public void saveWeatherResponses(List<WeatherResponseDto> responses) {
        /// 응답객체 리스트를 각각의 맵으로 변환하여 저장
        weatherLogService.saveAll(new HashMap<>());
        radiationLogService.saveAll(new HashMap<>());
        dailyWeatherService.saveAll(new HashMap<>());
    }
}
