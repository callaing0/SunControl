package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertDTO;
import com.suncontrol.mapper.MapperStory; // 바뀐 인터페이스 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final MapperStory mapperStory; // 타입 이름 변경

    public String checkAndCreateAlert(Double eff) {
        AlertDTO data = new AlertDTO();

        data.setLocation("Daejeon_Solar_Lab");
        data.setEfficiency(eff);

        if (eff < 80.0) {
            data.setStatus("WARNING");
            data.setMessage("태양광 효율 저하 감지: " + eff + "%");
            mapperStory.insertSolarLog(data); // 메서드 호출
            return "🚨 [경고] 발전 효율 저하 알림 저장됨";
        } else {
            data.setStatus("NORMAL");
            data.setMessage("정상 가동 중");
            mapperStory.insertSolarLog(data);
            return "✅ [정상] 발전 상태 양호";
        }
    }
}