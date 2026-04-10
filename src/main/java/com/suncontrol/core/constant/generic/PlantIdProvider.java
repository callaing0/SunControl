package com.suncontrol.core.constant.generic;

/// 맵에서 key로 사용하는 FK만 존재하는 필드를 만들어서
/// 리스트->맵 변환을 간편하게 만들 수 있다
public interface PlantIdProvider {
    public Long getPlantId();
}
