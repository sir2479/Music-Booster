package com.example.mub.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.mub.model.member.Member;

@Mapper
public interface MemberMapper {
	
	void saveMember(Member member);
	Member findMember(String member_id);
	Member findMemberByEmail(String member_email);
	Member findMemberByNickname(String member_nickname);
	void updateMember(Member member);
	void deleteMember(String Member_id);
	
}
