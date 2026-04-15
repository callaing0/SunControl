package com.suncontrol.mapper;

import com.suncontrol.core.entity.log.AlertLog;
import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.domain.dto.AlertSaveRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface Repository {
    List<AlertResponseDTO> findAll();

    AlertLog findById(@Param("id") Long id);

    void updateAlertProcess(
            @Param("id") Long id,
            @Param("status") int status,
            @Param("checkedAt") LocalDateTime checkedAt,
            @Param("resolvedAt") LocalDateTime resolvedAt
    );

    void insertAlert(AlertSaveRequestDTO dto);

    List<AlertResponseDTO> findByLocation(String location);

    int updateStatus(Long id, int status);
}