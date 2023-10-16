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

	public List<Article> showList() {
		
		List<Article> articles = new ArrayList<>();
		
		List<Map<String, Object>> articleListMap = articleDao.showList();
		
		for(Map<String,Object> articleMap : articleListMap) {
			articles.add(new Article(articleMap));
		}
		
		return articles;
	}

	public Article showDetail(int id) {
		Map<String, Object> map = articleDao.showDetail(id);
		
		if(map.isEmpty()) {
			return null;
		}
		
		Article article = new Article(map);
		return article;
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
}
