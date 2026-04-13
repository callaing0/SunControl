package com.suncontrol.mapper;

import com.suncontrol.domain.dto.AlertResponseDTO;
import com.suncontrol.domain.dto.AlertSaveRequestDTO;
import com.suncontrol.domain.dto.AlertUpdateStatusDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface Repository {

    // 1. 전체 조회 (List용)
    List<AlertResponseDTO> findAll();

    // 2. 지역별 조회 (이게 없어서 빨간 줄 뜬 겁니다! 🥊)
    List<AlertResponseDTO> findByLocation(String location);

    // 3. 데이터 삽입 (API 저장용)
    void insertAlert(AlertSaveRequestDTO dto);

    int updateStatus(Long id, int status);

    void updateAlertStatuses(List<AlertUpdateStatusDto> updateList);
}