package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertResponseDTO; // 바뀐 이름 확인!
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertListService {
    private final Repository repository;

    public List<AlertResponseDTO> getAlertList(String location) {
        if (location == null || location.isEmpty()) return repository.findAll();
        return repository.findByLocation(location);
    }
}
