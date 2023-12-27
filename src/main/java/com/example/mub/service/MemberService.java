package com.example.mub.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.mub.model.file.AttachedFile;
import com.example.mub.model.file.ImageFile;
import com.example.mub.model.member.Member;
import com.example.mub.repository.FileMapper;
import com.example.mub.repository.MemberMapper;
import com.example.mub.util.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberMapper memberMapper;
	private final FileService fileService;
	private final FileMapper fileMapper;
	
	@Value("${file.upload.path}")
	private String uploadPath;

	public Member findMember(String member_id) {
	return memberMapper.findMember(member_id);
	}
	
	public Member findMemberByEmail(String member_email) {
		return memberMapper.findMemberByEmail(member_email);
	}
	
	public Member findMemberByNickname(String member_nickname) {
		return memberMapper.findMemberByNickname(member_nickname);
	}

	public void saveMember(Member member) {
		memberMapper.saveMember(member);		
	}
	
	@Transactional
	public void updateMember(Member member, ImageFile imageFile, MultipartFile previousFile) {
		memberMapper.updateMember(member);						

	}
	
	public void deleteMember(String member_id) {
		log.info("삭제맵퍼전");
		log.info("member_id : {} ", member_id);
		memberMapper.deleteMember(member_id);
		log.info("삭제맵퍼후");
	}
	
	@Transactional
	public void removeImageFile(String member_id) {
		ImageFile imageFile = fileMapper.findImageFileByMemberId(member_id);
		if(imageFile != null) {
			String fullPath = uploadPath + "/" + imageFile.getFile_saved_name();
			fileService.deleteFile(fullPath);
			fileMapper.removeImageFileByMemberId(imageFile.getFile_member_id());
		}
	}
	
}
