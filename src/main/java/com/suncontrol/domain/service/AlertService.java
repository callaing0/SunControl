package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertDTO; // [중요] 경로 확인
import com.suncontrol.mapper.SolarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final SolarMapper solarMapper;

    public String checkAndCreateAlert(Double eff) {
        // 클래스 이름을 AlertDTO로 통일해서 생성
        AlertDTO data = new AlertDTO();

        // 여기서 빨간줄이 뜬다면 인텔리제이 상단
        // File -> Settings -> Annotation Processors에서 'Enable annotation processing' 체크!
        data.setLocation("Daejeon_Center");
        data.setEfficiency(eff);
        data.setStatus(eff < 80.0 ? "WARNING" : "NORMAL");
        data.setMessage("태양광 발전 효율: " + eff + "%");

        if (eff < 80.0) {
            solarMapper.insertSolarLog(data); // 매퍼의 파라미터 타입도 AlertDTO여야 함
            return "🚨 경고 데이터 저장됨";
        }
        return "✅ 정상 수치";
    }
}