package com.example.mub.model.member;


import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data

public class MemberUpdate {
	@NotNull
	private String member_id;
	@NotNull
	private String member_email;	
	@NotNull
	private String nickname;	
	@NotNull
	private String password;	
	@NotNull
	private String member_name;	
	@NotNull
	private String phone;	
	@NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @Past
	private LocalDate birthday;	
	@NotNull @DateTimeFormat(pattern="yyyy-MM-dd") @Past
	private LocalDate hire_date;	
	@NotNull
	private String profile;	
	
	public Member toMember(MemberUpdate memberUpdate) {
		Member member = new Member();
		member.setMember_id(memberUpdate.getMember_id());
		member.setMember_email(memberUpdate.getMember_email());
		member.setNickname(memberUpdate.getNickname());
		member.setPassword(memberUpdate.getPassword());
		member.setMember_name(memberUpdate.getMember_name());
		member.setPhone(memberUpdate.getPhone());
		member.setBirthday(memberUpdate.getBirthday());
		member.setHire_date(memberUpdate.getHire_date());
		member.setProfile(memberUpdate.getProfile());
		return member;
}
}