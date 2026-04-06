package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertDTO;
import com.suncontrol.mapper.SolarMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 주입 자동 생성
public class AlertService {

    private final SolarMapper solarMapper;

    public String checkAndCreateAlert(Double currentEfficiency) {
        if (currentEfficiency < 80.0) {
            AlertDTO alert = new AlertDTO();
            alert.setLocation("대전 선화동 본점");
            alert.setEfficiency(currentEfficiency);
            alert.setMessage("발전 효율 저하 경고! (" + currentEfficiency + "%)");
            alert.setStatus("PENDING");

            solarMapper.insertAlertLog(alert);
            return "🚨 알림 저장 완료! (DB 확인 요망)";
        }
        return "✅ 정상 수치입니다. (알림 미발생)";
    }
}