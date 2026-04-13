package com.suncontrol.domain.api;

import com.suncontrol.common.response.ResponseDto;
import com.suncontrol.core.dto.asset.PlantDto;
import com.suncontrol.core.service.asset.PlantService;
import com.suncontrol.domain.form.MemberCreateForm;
import com.suncontrol.domain.form.MemberUpdateForm;
import com.suncontrol.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final MemberService memberService;
    private final PlantService plantService;

    //관리자 페이지 발전 수동 데이터 발전소 선택을 위한 추가 코드
    @GetMapping("/plants")
    public ResponseDto<List<PlantDto>> getPlants() {
        return ResponseDto.of(true, "성공", plantService.findAllActive());
    }


    // 신규 회원 생성
    @PostMapping("/new")
    public ResponseEntity<ResponseDto<Void>> createMember(@RequestBody MemberCreateForm form) {
        memberService.createMember(form);
        return ResponseEntity.ok(ResponseDto.of(true, "성공", null));
    }
    // 회원 정보 수정
    @PutMapping("/members/{id}")
    public ResponseEntity<ResponseDto<Void>> updateMember(@PathVariable Long id,
                                                          @RequestBody MemberUpdateForm form) {
        memberService.updateMember(id, form);
        return ResponseEntity.ok(ResponseDto.of(true, "성공", null));
    }
    // 회원 계정 잠금 처리
    @PatchMapping("/members/{id}/lock")
    public ResponseEntity<ResponseDto<Void>> lockMember(@PathVariable Long id) {
        memberService.lockMember(id);
        return ResponseEntity.ok(ResponseDto.of(true, "성공", null));
    }
    // 회원 계정 잠금 해제 처리
    @PostMapping("/members/{id}/unlock")
    public ResponseEntity<ResponseDto<Void>> unlockMember(@PathVariable Long id) {
        memberService.unlockMember(id);
        return ResponseEntity.ok(ResponseDto.of(true, "성공", null));
    }
    // 입력한 아이디의 사용 가능 여부 확인
    @GetMapping("/check-id")
    public ResponseDto<Boolean> checkDuplicatedId(@RequestParam String userId) {
        boolean available = memberService.checkIdAvailability(userId);
        return ResponseDto.of(true, "성공", available);
    }
}