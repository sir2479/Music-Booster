package com.example.mub.model.member;


import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.board.model.member.Member;

import lombok.Data;

@Data
public class MemberUpdate {
	
	private String member_id;
	
	private String member_email;	
	@Size(min = 4, max = 20, message = "닉네임은 4~20사이로 입력해주세요.")
	private String nickname;	
	@Size(min = 4, max = 20, message = "비밀번호는 4~20사이로 입력해주세요.")
	private String password;	
	
	private String passwordCheck;
	
	private String member_name;	
	
	private String phone;	
	@DateTimeFormat(pattern="yyyy-MM-dd") @Past
	private LocalDate birthday;	
	
	private String position;
	@DateTimeFormat(pattern="yyyy-MM-dd") @Past
	private LocalDate hire_date;	

	private String profile;	
	
	public Member toMember(MemberUpdate memberUpdate) {
		Member member = new Member();
		member.setMember_id(memberUpdate.getMember_id());
		member.setMember_email(memberUpdate.getMember_email());
		member.setNickname(memberUpdate.getNickname());
		member.setPassword(memberUpdate.getPassword());
		member.setPasswordCheck(memberUpdate.getPasswordCheck());
		member.setMember_name(memberUpdate.getMember_name());
		member.setPhone(memberUpdate.getPhone());
		member.setPosition(memberUpdate.getPosition());
		member.setBirthday(memberUpdate.getBirthday());
		member.setHire_date(memberUpdate.getHire_date());
		member.setProfile(memberUpdate.getProfile());
		return member;
}
	
	
}