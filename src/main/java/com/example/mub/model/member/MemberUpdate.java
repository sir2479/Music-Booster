package com.example.mub.model.member;


import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberUpdate {
	
	private String member_id;
	
	private String member_email;	
	
	private String nickname;	
	
	private String password;	
	
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
		member.setMember_name(memberUpdate.getMember_name());
		member.setPhone(memberUpdate.getPhone());
		member.setPosition(memberUpdate.getPosition());
		member.setBirthday(memberUpdate.getBirthday());
		member.setHire_date(memberUpdate.getHire_date());
		member.setProfile(memberUpdate.getProfile());
		return member;
}
}