package com.suncontrol.core.repository.asset;

import com.suncontrol.core.entity.asset.Plant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlantRepository {
    /// 발전소 및 발전소 정보 뷰
    void save(Plant plant);

    void resetMain(@Param("memberId") Long memberId);

    void setMain(@Param("id") Long plantId);

    boolean existsByIdAndMemberId(@Param("memberId") Long memberId, @Param("id") Long plantId);

    List<Plant> findAllByIsDeletedFalse();
}
