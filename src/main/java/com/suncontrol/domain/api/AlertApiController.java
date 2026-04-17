package com.suncontrol.domain.api;

import com.suncontrol.core.constant.alert.AlertStatus;
import com.suncontrol.domain.dto.AlertSaveRequestDTO;
import com.suncontrol.domain.service.AlertSaveService;
import com.suncontrol.domain.service.AlertProcessService; // 추가
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; // 추가
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts") // HTML fetch 주소와 일치시킴
@RequiredArgsConstructor
public class AlertApiController {

    private final AlertSaveService alertSaveService;
    private final AlertProcessService alertProcessService; // 조치 로직이 있는 서비스 추가

    /**
     * 기존 테스트용 저장 API
     */
    @PostMapping("/test-alert") // 주소가 중복되지 않게 살짝 수정
    public String receiveAlert(@RequestBody AlertSaveRequestDTO dto) {
        alertSaveService.saveAlertData(dto);
        return "success";
    }

    /**
     * [추가] 화면에서 '대기' 버튼 클릭 시 호출되는 실제 조치 완료 API
     * URL: POST /api/alerts/resolve/{id}
     */
    @PostMapping("/resolve/{id}")
    public ResponseEntity<String> resolveAlert(@PathVariable Long id) {
        try {
            // 1. DB에서 현재 알람의 상태 코드를 직접 가져옵니다.
            int currentStatusCode = alertProcessService.getStatusCode(id);

            // 2. 상태에 따른 단계별 업데이트
            if (currentStatusCode == 0) { // PENDING (대기)
                alertProcessService.updateStatus(id, 1); // 1: PROCESSING(확인 중)으로 변경
                return ResponseEntity.ok("로그 [" + id + "]번 확인을 시작합니다. 상태가 '확인 중'으로 변경되었습니다.");
            }
            else if (currentStatusCode == 1) { // PROCESSING (확인 중)
                alertProcessService.updateStatus(id, 2); // 2: RESOLVED(조치완료)로 변경
                return ResponseEntity.ok("로그 [" + id + "]번 조치가 완료되었습니다.");
            }
            else { // 이미 2(RESOLVED)인 경우
                return ResponseEntity.ok("이미 조치가 완료된 항목입니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("상태 변경 중 오류: " + e.getMessage());
        }
    }
}