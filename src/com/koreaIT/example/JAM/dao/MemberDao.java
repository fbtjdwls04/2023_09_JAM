package com.koreaIT.example.JAM.dao;

import java.sql.Connection;
import java.util.Map;

import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class MemberDao {
	
	private Connection conn;
	
	public MemberDao(Connection conn) {
		this.conn = conn;
	}

	public boolean isLoginDup(String loginId) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) > 0");
		sql.append("FROM members");
		sql.append("WHERE loginId = ?", loginId);
		
		return DBUtil.selectRowBooleanValue(conn, sql);
	}

	public void doJoin(String loginId, String loginPw, String name) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO members");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("loginId = ?,",loginId);
		sql.append("loginPw = SHA2(?, 256),",loginPw);
		sql.append("`name` = ?",name);
		
		DBUtil.insert(conn, sql);
	}

	public Map<String, Object> doLogin(String loginId, String loginPw) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM members");
		sql.append("WHERE loginId = ?",loginId);
		sql.append("AND loginPw = SHA2(?, 256)",loginPw);
		
		return DBUtil.selectRow(conn, sql);
	}
}
