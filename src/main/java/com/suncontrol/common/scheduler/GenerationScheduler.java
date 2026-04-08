package com.suncontrol.common.scheduler;

import com.suncontrol.common.service.GenerationEnergyService;
import com.suncontrol.common.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class GenerationScheduler {
    /// todo 예측모델 구현
//    private final ActualReportService actualReportService;
//    private final PredictReportService predictReportService;
    private final WeatherApiService weatherApiService;
    private final GenerationEnergyService generationEnergyService;

    private final Integer GENERATE_INTERVAL = 3600;
    @Scheduled(cron = "0 0 * * * *")
    private void realtimeBatch() {
        log.info("Realtime batch start at {}", LocalDateTime.now());
        collectWeatherInfo();
        collectGenerateData();
    }

    private void collectWeatherInfo() {
        /// 매 N시 5분 마다 업데이트되는 기상정보 수집
        /// 정각보다는 약간 늦게 호출해서 최신정보를 수집하기 위함.
//        LocalDateTime now = LocalDateTime.now();
//        if(now.getMinute() > 10)
//            return; TODO : 진짜 일할시간인지 아닌지는 오케스트레이터가 결정할 문제

        try {
            weatherApiService.requestAndSaveWeather();
        } catch (Exception e) {
            log.error("기상 정보 수집 중 오류 발생: {}", e.getMessage(), e);
        }
    }

    private void collectGenerateData() {
        log.info("generate energy data at {}", LocalDateTime.now());
        generationEnergyService.generateEnergy(GENERATE_INTERVAL);
        log.info("발전데이터 생성 완료");
    }

    public void init() {
        log.info("server initializing process starts at {}", LocalDateTime.now());
        collectWeatherInfo();
        collectGenerateData();
    }
}
