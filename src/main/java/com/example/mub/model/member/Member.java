package com.example.mub.model.member;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Member {
	private String member_id;	
	private String member_email;	
	private String nickname;	
	private String password;	
	private String member_name;	
	private String phone;	
	private LocalDate birthday;	
	private LocalDate hire_date;	
	private String position;	
	private String profile;	
	

	
}
