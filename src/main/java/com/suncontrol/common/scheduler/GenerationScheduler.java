package com.suncontrol.common.scheduler;

import com.suncontrol.common.service.GenerationEnergyService;
import com.suncontrol.common.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerationScheduler {
    /// todo
//    private final ActualReportService actualReportService;
//    private final PredictReportService predictReportService;
    private final WeatherApiService weatherApiService;
    private final GenerationEnergyService generationEnergyService;

    @Scheduled(cron = "0 5 * * * *")
    public void collectWeatherInfo() {
        /// 매 N시 5분 마다 업데이트되는 기상정보 수집
        /// 정각보다는 약간 늦게 호출해서 최신정보를 수집하기 위함.

        try {
            weatherApiService.requestAndSaveWeather();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
