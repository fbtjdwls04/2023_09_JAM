package com.koreaIT.example.JAM.service;

import java.sql.Connection;
import java.util.Map;

import com.koreaIT.example.JAM.Member;
import com.koreaIT.example.JAM.dao.MemberDao;

public class MemberService {
	
	private MemberDao memberDao;
	
	public MemberService(Connection conn) {
		this.memberDao = new MemberDao(conn);
	}
	
	public boolean isLoginDup(String loginId) {
		return memberDao.isLoginDup(loginId);
	}

	public void doJoin(String loginId, String loginPw, String name) {
		memberDao.doJoin(loginId, loginPw, name);
	}

	public Member doLogin(String loginId, String loginPw) {
		Map<String, Object> memberMap = memberDao.doLogin(loginId, loginPw);
		
		if(memberMap.isEmpty()) {
			return null;
		}
		return  new Member(memberMap);
	}

}
