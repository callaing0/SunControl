package com.suncontrol.domain.service;

import com.suncontrol.domain.dto.AlertDTO;
import com.suncontrol.mapper.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final Repository repository;

    public List<AlertDTO> getAlertList(String location) {
        Repository Repository = null;
        if (location == null || location.trim().isEmpty()) {
            return Repository.findAll();
        }
        return Repository.findByLocation(location);
    }
}