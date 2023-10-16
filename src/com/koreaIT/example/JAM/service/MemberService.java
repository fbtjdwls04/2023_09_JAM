package com.koreaIT.example.JAM.service;

import java.sql.Connection;

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

}
