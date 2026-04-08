package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertDTO;
import com.suncontrol.mapper.Repository; // 수정된 인터페이스 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    // 주입받는 타입 이름을 Repository로 변경
    private final Repository repository;

    public List<AlertDTO> getAlertList() {
        return repository.findAllAlerts();
    }
}