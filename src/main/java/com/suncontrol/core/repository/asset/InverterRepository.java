package com.suncontrol.core.repository.asset;

import com.suncontrol.core.dto.asset.InverterCapSurplusDto;
import com.suncontrol.core.dto.asset.InverterUpdateDto;
import com.suncontrol.core.entity.asset.Inverter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InverterRepository {
    void save(Inverter inverter);

    void updateCap(InverterCapSurplusDto dto);

    List<Inverter> findAllByStatusCodeBetween(@Param("start") int start,@Param("end") int end);

    int updateLastAccumAndStatus(List<InverterUpdateDto> list);

    List<Inverter> findAllByPlantId(@Param("plantId") Long plantId);
}
