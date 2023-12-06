package com.example.mub.model.member;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;



@Data
public class MemberSignup {
	
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
	@NotNull
	private String position;	
	
	public static Member toMember(MemberSignup memberSignup) {
		Member member = new Member();
		member.setMember_id(memberSignup.getMember_id());
		member.setMember_email(memberSignup.getMember_email());
		member.setNickname(memberSignup.getNickname());
		member.setPassword(memberSignup.getPassword());
		member.setMember_name(memberSignup.getMember_name());
		member.setPhone(memberSignup.getPhone());
		member.setBirthday(memberSignup.getBirthday());
		member.setPosition(memberSignup.getPosition());
		return member;
	}
	
	
}
