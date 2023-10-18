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

	public int doWrite(int memberId,String title, String body) {
		return articleDao.doWrite(memberId,title, body);
	}

	public List<Article> showList() {
		List<Map<String, Object>> articleMap = articleDao.showList();
		
		List<Article> articles = new ArrayList<>();

		for(Map<String, Object> map : articleMap) {
			Article article = new Article(map);
			articles.add(article);
		}
		
		return articles;
	}

	public Article showDetail(int id) {
		Map<String, Object> articleMap = articleDao.showDetail(id); 
		
		if(articleMap.isEmpty()) {
			return null;
		}
		
		return new Article(articleMap);
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

	public boolean isAuthority(int id, int memberId) {
		return articleDao.isAuthority(id, memberId);
	}
}
