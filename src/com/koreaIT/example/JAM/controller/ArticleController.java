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

	public void showList(String cmd) {
		System.out.println("==게시물 목록==");
		
		String searchKeyword = cmd.substring("article list".length()).trim();

		List<Article> articles = articleService.showList(searchKeyword);
		
		if(searchKeyword.length() > 0) {
			
			System.out.println("검색어 : " + searchKeyword);
			
			if(articles.size() == 0) {
				System.out.println("검색결과가 없습니다.");
				return;
			}
			
		}else if(articles.size() == 0){
			System.out.println("게시글이 없습니다.");
			return;
		}
		
		
		System.out.println("번호  /  제목  /  내용  / 작성자");
		for(Article article : articles) {
			System.out.printf("%d  /  %s  /  %s  /  %s \n",article.id,article.title,article.body,article.name);
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
		System.out.printf("번  호 : %d  \n",article.id);
		System.out.printf("작성일 : %s  \n",Util.datetimeFormat(article.regDate));
		System.out.printf("수정일 : %s  \n",Util.datetimeFormat(article.updateDate));
		System.out.printf("작성자 : %s  \n",article.name);
		System.out.printf("제  목 : %s  \n",article.title);
		System.out.printf("내  용 : %s  \n",article.body);
	}

	public void doModify() {
		
		if(Session.isLogined() == false) {
			System.out.println("로그인 후 사용이 가능합니다.");
			return;
		}
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n",id);
			return;
		}
		
		if(article.memberId != Session.loginedMemberId) {
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
		
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n",id);
			return;
		}
		
		if(article.memberId != Session.loginedMemberId) {
			System.out.println("권한이 없습니다.");
			return;
		}
		articleService.doDelete(id);
		
		System.out.printf("%d번 게시물이 삭제되었습니다.\n",id);
	}
}
