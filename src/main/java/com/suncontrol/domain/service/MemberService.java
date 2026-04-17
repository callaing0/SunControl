package com.suncontrol.domain.service;

import com.suncontrol.core.entity.Member;
import com.suncontrol.core.repository.report.MemberRepository;
import com.suncontrol.domain.form.MemberCreateForm;
import com.suncontrol.domain.form.MemberUpdateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    // 회원 DB 처리용
    private final MemberRepository memberRepository;
    // 비밀번호 암호화 객체
    private final PasswordEncoder passwordEncoder;

    // 전체 회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
    // 회원 id로 회원 조회
    public Member findById(Long id) {
        return memberRepository.findById(id);
    }
    // 로그인 id로 회원 조회
    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId);
    }
    // 신규 회원 생성
    public void createMember(MemberCreateForm form) {

        Member member = new Member();
        // 입력받은 사용자 정보 세팅
        member.setUserId(form.getUserId());
        member.setName(form.getName());
        member.setAffiliation(form.getAffiliation());
        member.setRole(form.getRole());
        // 초기 비밀번호 1234로 임시 저장
        member.setPassword(passwordEncoder.encode("1234"));
        // 최초 로그인시 비밀번호 변경 대상
        member.setTemporary(true);
        // 기본 상태는 활성화
        member.setStatus("ACTIVE");
        // 등록/수정 시간 저장
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());

        memberRepository.insertMember(member);
    }
    // 회원 정보 수정
    public void updateMember(Long id, MemberUpdateForm form) {

        Member member = memberRepository.findById(id);
        // 회원이 없으면 종료
        if (member == null) return;
        // 수정 가능한 값
        member.setRole(form.getRole());
        member.setStatus(form.getStatus());
        member.setName(form.getName());
        member.setAffiliation(form.getAffiliation());
        member.setUpdatedAt(LocalDateTime.now());

        memberRepository.updateMember(member);
    }
    // 아이디 중복 여부 확인
    @Transactional(readOnly = true)
    public boolean checkIdAvailability(String userId) {
        return memberRepository.findByUserId(userId) == null;
    }
    // 회원 잠금 처리
    public void lockMember(Long id) {
        memberRepository.lockMember(id);
    }
    // 회원 잠금 해제 처리
    public void unlockMember(Long id) {
        memberRepository.unlockMember(id);
    }

    // 비밀번호 변경 처리
    public void changePassword(Member member, String encodedPassword) {
        memberRepository.updatePassword(member.getId(), encodedPassword);
        memberRepository.updateFirstLogin(member.getId(), false);
    }
}