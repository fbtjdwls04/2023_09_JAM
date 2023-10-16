package com.koreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.example.JAM.Article;
import com.koreaIT.example.JAM.service.ArticleService;

public class ArticleController extends Controller {
	
	private ArticleService articleService;
	private Scanner sc;
	private String cmd;
	
	public ArticleController(Connection conn, Scanner sc, String cmd) {
		articleService = new ArticleService(conn);
		this.sc = sc;
		this.cmd = cmd;
	}

	public void doWrite() {
		System.out.println("==게시물 작성==");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int id = articleService.doWrite(title, body);
		
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
			System.out.printf("%d  /  %s  /  %s  /  %s\n",article.id, article.title, article.body, article.regDate);
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
		System.out.printf("%d  /  %s  /  %s  /  %s\n",article.id, article.title, article.body, article.regDate);
	}

	public void doModify() {
		System.out.println("==게시물 수정==");
		
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		int articleCount = articleService.articleCount(id);
		
		if(articleCount == 0) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n",id);
			return;
		}
		
		System.out.printf("새 제목 : ");
		String newTitle = sc.nextLine();
		System.out.printf("새 내용 : ");
		String newBody = sc.nextLine();
		
		articleService.doModify(id, newTitle, newBody);
		System.out.printf("%d번 글이 수정되었습니다.\n",id);
		
	}

	public void doDelete() {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		int affectedRow = articleService.doDelete(id);
		
		if(affectedRow == 0) {
			System.out.printf("%d번 게시물이 존재하지 않습니다\n",id);
			return;
		}
		
		System.out.printf("%d번 게시물이 삭제되었습니다.\n",id);
	}
}
