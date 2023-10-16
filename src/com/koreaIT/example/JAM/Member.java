package com.koreaIT.example.JAM;

import java.time.LocalDateTime;
import java.util.Map;

public class Member {
	
	public int id;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public String loginId;
	public String loginPw;
	public String name;
	
	Member(int id, String loginId, String name){
		this.id = id;
		this.loginId = loginId;
		this.name = name;
	}
	
	Member(Map<String, Object> MemberMap){
		this.id = (int) MemberMap.get("id");
		this.regDate = (LocalDateTime) MemberMap.get("regDate");
		this.loginId = (String) MemberMap.get("loginId");
		this.loginPw = (String) MemberMap.get("loginPw");
		this.name = (String) MemberMap.get("name");
	}
}
