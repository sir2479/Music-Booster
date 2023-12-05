package com.example.mub.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.mub.model.member.Member;

@Mapper
public interface MemberMapper {
	
	void saveMember(Member member);
	Member findMember(String member_id);
	void updateMember(Member updateMember);
	void deleteMember(Long Member_id);
	
}
