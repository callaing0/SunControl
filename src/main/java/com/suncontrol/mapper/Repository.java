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

    // @Param을 통해 XML의 #{id}와 매칭
    AlertLog findById(@Param("id") Long id);

    // 여러 파라미터가 들어갈 때는 @Param이 필수입니다.
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