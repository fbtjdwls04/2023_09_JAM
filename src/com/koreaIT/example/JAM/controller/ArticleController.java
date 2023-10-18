package com.koreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.example.JAM.Article;
import com.koreaIT.example.JAM.service.ArticleService;
import com.koreaIT.example.JAM.session.Session;
import com.koreaIT.example.JAM.util.Util;

public class ArticleController{
	
	private ArticleService articleService;
	private Scanner sc;
	private String cmd;
	
	public ArticleController(Connection conn, Scanner sc, String cmd) {
		articleService = new ArticleService(conn);
		this.sc = sc;
		this.cmd = cmd;
	}

	public void doWrite() {
		
		if(Session.isLogined() == false) {
			System.out.println("로그인 후 사용이 가능합니다.");
			return;
		}
		
		System.out.println("==게시물 작성==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		
		int id = articleService.doWrite(Session.loginedMemberId,title, body);
		
		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
	}

	public void showList() {
		System.out.println("==게시물 목록==");
		List<Article> articles = articleService.showList();
		
		if(articles.size() == 0) {
			System.out.println("게시글이 없습니다.");
			return;
		}
		
		System.out.println("번호/제목");
		for(Article article : articles) {
			System.out.printf("%d  /  ",article.id);
			System.out.printf("%s  /  ",Util.datetimeFormat(article.regDate));
			System.out.printf("%s  /  ",Util.datetimeFormat(article.updateDate));
			System.out.printf("%s  /  ",article.loginId);
			System.out.printf("%s  /  ",article.title);
			System.out.printf("%s  \n",article.body);
		}
	}

	public void showDetail() {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		Article article = articleService.showDetail(id);
		
		if(article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}
		
		System.out.println("==게시물 상세보기==");
		System.out.printf("%d  /  ",article.id);
		System.out.printf("%s  /  ",Util.datetimeFormat(article.regDate));
		System.out.printf("%s  /  ",Util.datetimeFormat(article.updateDate));
		System.out.printf("%s  /  ",article.loginId);
		System.out.printf("%s  /  ",article.title);
		System.out.printf("%s  \n",article.body);
	}

	public void doModify() {
		
		if(Session.isLogined() == false) {
			System.out.println("로그인 후 사용이 가능합니다.");
			return;
		}
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		int articleCount = articleService.articleCount(id);
		
		if(articleCount == 0) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n",id);
			return;
		}
		
		if(articleService.isAuthority(id, Session.loginedMemberId) == false) {
			System.out.println("권한이 없습니다.");
			return;
		}
		
		System.out.println("==게시물 수정==");
		System.out.printf("새 제목 : ");
		String newTitle = sc.nextLine();
		System.out.printf("새 내용 : ");
		String newBody = sc.nextLine();
		
		articleService.doModify(id, newTitle, newBody);
		System.out.printf("%d번 글이 수정되었습니다.\n",id);
		
	}

	public void doDelete() {
		
		if(Session.isLogined() == false) {
			System.out.println("로그인 후 사용이 가능합니다.");
			return;
		}
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		int articleCount = articleService.articleCount(id);
		
		if(articleCount == 0) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n",id);
			return;
		}
		
		if(articleService.isAuthority(id, Session.loginedMemberId) == false) {
			System.out.println("권한이 없습니다.");
			return;
		}
		
		articleService.doDelete(id);
		
		System.out.printf("%d번 게시물이 삭제되었습니다.\n",id);
	}
}
