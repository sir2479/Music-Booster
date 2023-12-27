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
	
	private String passwordCheck;
	private String image_file_saved_name;
	
	public boolean isPasswordConfirmed(String password, String passwordCheck) {
		
		if(password == passwordCheck) {
			return true;
		} else {
			return false;
		}
	}
}


