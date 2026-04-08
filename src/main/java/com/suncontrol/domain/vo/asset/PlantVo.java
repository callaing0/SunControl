package com.suncontrol.domain.vo.asset;

import com.suncontrol.core.dto.asset.PlantDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PlantVo {
    ///  발전소 목록 표시용(드롭다운, 리스트)
    private Long id;
    private Long memberId; /// 굳이 필요할지는 모르겠으나 크로스체크용
    private String name;

    public PlantVo(PlantDto dto) {
        if(dto == null) return;
        this.id = dto.getId();
        this.memberId = dto.getMemberId();
        this.name = dto.getName();
    }

    public PlantVo(PlantVo other) {
        if(other == null) return;
        this.id = other.getId();
        this.memberId = other.getMemberId();
        this.name = other.getName();
    }
}
