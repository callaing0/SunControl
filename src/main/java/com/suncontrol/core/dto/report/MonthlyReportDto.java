package com.suncontrol.core.dto.report;

import com.suncontrol.core.constant.generic.InverterIdProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyReportDto implements InverterIdProvider {
    private Long inverterId;
}
