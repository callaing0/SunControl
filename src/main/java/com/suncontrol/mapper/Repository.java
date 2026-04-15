package com.suncontrol.mapper;

import com.suncontrol.core.entity.log.AlertLog;
import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.domain.dto.AlertSaveRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface Repository {

    void insertAlert(AlertSaveRequestDTO dto);

    List<AlertResponseDTO> findAll();

    List<AlertResponseDTO> findByLocation(String location);

    AlertLog findAlertById(Long id);      // 추가
    void updateAlertStatus(AlertLog alert); // 추가
}