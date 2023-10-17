package com.koreaIT.example.JAM.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.koreaIT.example.JAM.Article;
import com.koreaIT.example.JAM.dao.ArticleDao;

public class ArticleService {

	ArticleDao articleDao;
	
	public ArticleService(Connection conn){
		articleDao = new ArticleDao(conn);
	}

	public int doWrite(String title, String body) {
		return articleDao.doWrite(title, body);
	}

	public List<Map<String, Object>> showList() {
		return articleDao.showList();
	}

	public Map<String, Object> showDetail(int id) {
		return articleDao.showDetail(id);
	}

	public void doModify(int id, String newTitle, String newBody) {
		articleDao.doModify(id, newTitle, newBody);
	}

	public int doDelete(int id) {
		return articleDao.doDelete(id);
	}

	public int articleCount(int id) {
		return articleDao.articleCount(id);
	}

	public boolean isAuthority(int id) {
		return articleDao.isAuthority(id);
	}
}
