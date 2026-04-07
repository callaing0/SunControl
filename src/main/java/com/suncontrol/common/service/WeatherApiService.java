package com.suncontrol.common.service;

import com.suncontrol.common.dto.api.WeatherRequestDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherApiService {
    /// 기상 조회 API 서비스

    private final WeatherLogService weatherLogService;
    private final RadiationLogService radiationLogService;
    private final DailyWeatherService dailyWeatherService;
    private final PlantService plantService;
    private final RestTemplate restTemplate;

    public void requestAndSaveWeather() {
        List<PlantDto> plants = plantService.findAllActive();
        List<PlantWeatherApiDto> plantWeatherApiList =
                plants.stream().map(PlantWeatherApiDto::new).toList();
        /// 기상 정보 요청해서 각 서비스의 필요정보 추출하여 저장
        List<WeatherResponseDto> responses = getWeatherResponses(plantWeatherApiList);

        saveWeatherResponses(responses);
    }
    public String buildOpenMeteoRequestUrl(WeatherRequestDto request) {
        return UriComponentsBuilder.fromUriString("https://api.open-meteo.com/v1/forecast")
                .queryParam("latitude", request.getLatitude())
                .queryParam("longitude", request.getLongitude())
                .queryParam("timezone", request.getTimezone())
                .queryParam("hourly", request.getHourly())
//                .queryParam("daily", request.getDaily())
//                .queryParam("tilt", request.getTilt())
//                .queryParam("azimuth", request.getAzimuth())
//                .queryParam("past_days", request.getPastDays())
//                .queryParam("forecast_days", request.getForecastDays())
                .build().toUriString();
    }

    private List<WeatherResponseDto> getWeatherResponses(List<PlantWeatherApiDto> plants) {
        // TODO "진짜로 Open-Meteo 접속해서 데이터 가져오는 것"
        List<WeatherResponseDto> responses = new ArrayList<>();
        for(PlantWeatherApiDto plant : plants) {
            try {
                String requestUrl = buildOpenMeteoRequestUrl(new WeatherRequestDto(plant));

                log.info("requestUrl: {}", requestUrl);
                WeatherResponseDto response =
                        restTemplate.getForObject(requestUrl, WeatherResponseDto.class);

                if (response != null) {
                    response.setPlant(plant);
                    responses.add(response);
                    log.info("발전소 {} 날씨정보 수집완료 {}", plant.getId(), response);
                }
            } catch (Exception e) {
                log.error("발전소 {} 날씨정보 수집중 오류 발생 : {}", plant.getId(), e.getMessage());
            }
        }

        return responses;
    }

    @Transactional
    public void saveWeatherResponses(List<WeatherResponseDto> responses) {
        if(responses.isEmpty()) return;
        /// 응답객체 리스트를 각각의 리스트로 변환하여 저장
        weatherLogService.saveAll(
                responses
                        // List<WeatherResponseDto>를
                        // Stream<T> 객체로 변환
                        .stream()
                        // WeatherResponseDto
                        // 내부의 컬렉션 객체를
                        // 바깥으로 꺼내기
                        .flatMap(
                                // WeatherResponseDto
                                // 내부 메서드를 이용하기 위한
                                // 익명함수
                                res -> res
                                        // WeatherLogDto 리스트를
                                        // 반환한다
                                        .getWeatherLogs()
                                        // 변환이 용이하게
                                        // Stream 객체로 바꾼다
                                        .stream()
                        )
                        // 리스트로 바꾼다
                        .toList()
        );

        radiationLogService.saveAll(
                responses
                        .stream()
                        .flatMap(
                                res -> res
                                        .getRadiationLogs()
                                        .stream()
                        )
                        .toList()
        );

        dailyWeatherService.saveAll(
                responses
                        .stream()
                        .flatMap(
                                res -> res
                                        .getDailyWeathers()
                                        .stream()
                        )
                        .toList()
        );
    }
}
