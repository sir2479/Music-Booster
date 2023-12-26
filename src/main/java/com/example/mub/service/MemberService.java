package com.example.mub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mub.model.member.Member;
import com.example.mub.repository.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberMapper memberMapper;

	public Member findMember(String member_id) {
	return memberMapper.findMember(member_id);
	}

	@Transactional
	public void saveMember(Member member) {
		log.info("멥퍼전");
		log.info("member: {}", member);
		memberMapper.saveMember(member);
		log.info("멥퍼후");
		
	}
	

	
}
