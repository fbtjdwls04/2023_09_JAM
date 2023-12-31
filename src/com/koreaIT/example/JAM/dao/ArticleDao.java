package com.koreaIT.example.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class ArticleDao {
	
	private Connection conn;
	
	public ArticleDao(Connection conn) {
		this.conn = conn;
	}

	public int doWrite(int memberId,String title, String body) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("memberId = ?,",memberId);
		sql.append("title = ?,",title);
		sql.append("`body` = ?",body);
		
		return DBUtil.insert(conn, sql);
	}
	
	public List<Map<String, Object>> showList(String keyword) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.id");
		sql.append(", A.regDate , A.updateDate ,A.memberId , M.name , A.title , A.body");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN members AS M");
		sql.append("ON A.memberId = M.id");
		if(keyword.length() > 0) {
			sql.append("WHERE A.title LIKE CONCAT('%', ? , '%')", keyword);
		}
		sql.append("ORDER BY id DESC");
		
		return DBUtil.selectRows(conn, sql);
	}

	public Map<String, Object> showDetail(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.id");
		sql.append(", A.regDate , A.updateDate , A.memberId , M.name , A.title , A.body");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN members AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("WHERE A.id = ?",id);
		
		return DBUtil.selectRow(conn, sql);
	}
	
	public void doModify(int id, String newTitle, String newBody) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET title = ?,",newTitle);
		sql.append("body = ?,",newBody);
		sql.append("updateDate = NOW()");
		sql.append("WHERE id = ?",id);
		
		DBUtil.update(conn, sql);
	}

	public int doDelete(int id) {
		SecSql sql = new SecSql();
		sql.append("DELETE");
		sql.append("FROM article");
		sql.append("WHERE id = ?",id);
		
		return DBUtil.delete(conn, sql);
	}

	public int articleCount(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRowIntValue(conn, sql);
	}

	public Map<String, Object> getArticle(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRow(conn, sql);
	}
}
