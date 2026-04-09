package com.suncontrol.core.repository.asset;

import com.suncontrol.core.entity.asset.Panel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PanelRepository {
    // 패널 CRUD
    public List<Panel> findAllByInverterIds (List<Long> inverters);

    public Optional<Panel> findById (Long id);

    public int save (Panel panel);

    public int update (Panel panel);

    public int delete (Long id);

    List<Panel> findAllofInverters(List<Long> inverters);
}
