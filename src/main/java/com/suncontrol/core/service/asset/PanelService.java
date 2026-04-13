package com.suncontrol.core.service.asset;

import com.suncontrol.core.dto.asset.PanelDto;
import com.suncontrol.core.entity.asset.Panel;
import com.suncontrol.core.repository.asset.PanelRepository;
import com.suncontrol.core.util.DataCollectorsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PanelService {
    private final PanelRepository repository;

    public void save(PanelDto dto) {
        int result = repository.save(new Panel(dto));
    }

    public Map<Long, List<PanelDto>> findDetailsByInverters(List<Long> inverters) {
        //todo 마이페이지 '패널 정보' 용 데이터 조회
        // MyPageService 에서 inverterId 리스트를 전달받아 패널 정보를 반환한다
        // 0번 key (Long 0L) 에는 지정해 준 모든 인버터의 정보를 합산하여 저장한다.
        // 더미데이터 다 밀어넣고 기상 조회/저장하고 한 8일~10일쯤에 구현예정.

        if (inverters == null || inverters.isEmpty()) {
            return Map.of(); // 여기 없으면 또 터진다
        }

        Map<Long, List<PanelDto>> panelMap = DataCollectorsUtil.groupBy(
                repository.findAllByInverterIds(inverters),
                Panel::getInverterId,
                PanelDto::new
        );

        panelMap.put(0L, DataCollectorsUtil.toDataList(
                repository.findAllofInverters(inverters),
                PanelDto::new
                ));

        return panelMap;
    }
}
