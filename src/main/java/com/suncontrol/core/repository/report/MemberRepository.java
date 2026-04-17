package com.suncontrol.core.repository.report;

import com.suncontrol.core.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberRepository {

    // 전체 회원 조회
    List<Member> findAll();
    // 회원 id로 조회
    Member findById(Long id);
    // 로그인 id로 조회
    Member findByUserId(String userId);
    // 신규 회원 저장
    void insertMember(Member member);
    // 회원 정보 수정
    void updateMember(Member member);
    // 회원 상태 잠금
    void lockMember(Long id);
    // 회원 상태 잠금 해제
    void unlockMember(Long id);
    // 비밀번호 변경
    void updatePassword(@Param("id") Long id,
                        @Param("password") String password);
    // 최초 로그인 여부 변경
    void updateFirstLogin(@Param("id") Long id,
                          @Param("isTemporary") boolean isTemporary);
}
