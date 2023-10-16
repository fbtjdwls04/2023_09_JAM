package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.example.JAM.controller.ArticleController;
import com.koreaIT.example.JAM.controller.Controller;
import com.koreaIT.example.JAM.controller.MemberController;
import com.koreaIT.example.JAM.util.DBUtil;
import com.koreaIT.example.JAM.util.SecSql;

public class App {
	
	public void start() {
		System.out.println("=프로그램 시작==");
		
		
		Controller controller = null;
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.print("명령어 ) ");
			String cmd = sc.nextLine().trim();
			
			Connection conn = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");

			} catch (ClassNotFoundException e) {
				System.out.println("드라이버 로딩 실패");
				e.printStackTrace();
			}
 
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			try {
				conn = DriverManager.getConnection(url, "root", "");

				int actionRs = doAction(conn, cmd, sc);

				if (actionRs == -1) {
					System.out.println("==프로그램 종료==");
					break;
				}

			} catch (SQLException e) {
				System.out.println("에러 : " + e);
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		sc.close();

	}

	private int doAction(Connection conn, String cmd, Scanner sc) {
		MemberController memberController = new MemberController(conn, sc);
		ArticleController articleController = new ArticleController(conn, sc);
		
		if (cmd.equals("exit")) {
			return -1;
		}
		if(cmd.equals("member join")) {
			memberController.doJoin();
		}
		else if(cmd.equals("member login")) {
			memberController.doLogin();
		}
		// 게시물 작성
		else if (cmd.equals("article write")) {
			System.out.println("==게시물 작성==");
			System.out.printf("제목 : ");
			String title = sc.nextLine();
			System.out.printf("내용 : ");
			String body = sc.nextLine();

			SecSql sql = new SecSql();
			sql.append("INSERT INTO article");
			sql.append("SET regDate = NOW(),");
			sql.append("updateDate = NOW(),");
			sql.append("title = ?,",title);
			sql.append("`body` = ?",body);
			
			int id = DBUtil.insert(conn, sql);
			
			System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
			
		} 
		// 게시물 목록
		else if (cmd.equals("article list")) {
			System.out.println("==게시물 목록==");
			List<Article> articles = new ArrayList<>();
			
			SecSql sql = new SecSql();
			sql.append("SELECT * FROM article ORDER BY id DESC");
			List<Map<String, Object>> list = DBUtil.selectRows(conn, sql);
			
			for(Map<String,Object> articleMap : list) {
				articles.add(new Article(articleMap));
			}
			
			if(articles.size() == 0) {
				System.out.println("게시글이 없습니다.");
				return 0;
			}
			
			System.out.println("번호/제목");
			for(Article article : articles) {
				System.out.printf("%d  /  %s  /  %s  /  %s\n",article.id, article.title, article.body, article.regDate);
			}
		} 
		// 게시물 디테일
		else if (cmd.startsWith("article detail")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			SecSql sql = new SecSql();
			sql.append("SELECT *");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			Map<String,Object> articleMap = DBUtil.selectRow(conn, sql);
			
			if(articleMap.isEmpty()) {
				System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
				return 0;
			}
			System.out.println("==게시물 상세보기==");
			Article article = new Article(articleMap);
			System.out.printf("%d  /  %s  /  %s  /  %s\n",article.id, article.title, article.body, article.regDate);
			
		}
		// 게시물 수정
		else if (cmd.startsWith("article modify")) {
			System.out.println("==게시물 수정==");
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(*) > 0");
			sql.append("FROM article");
			sql.append("WHERE id = ?", id);
			
			int articleCount = DBUtil.selectRowIntValue(conn, sql);
			
			if(articleCount == 0) {
				System.out.printf("%d번 게시물은 존재하지 않습니다.\n",id);
				return 0;
			}
			
			System.out.printf("새 제목 : ");
			String newTitle = sc.nextLine();
			System.out.printf("새 내용 : ");
			String newBody = sc.nextLine();
			
			sql = new SecSql();
			sql.append("UPDATE article");
			sql.append("SET title = ?,",newTitle);
			sql.append("body = ?,",newBody);
			sql.append("updateDate = NOW()");
			sql.append("WHERE id = ?",id);
			
			DBUtil.update(conn, sql);
			System.out.printf("%d번 글이 수정되었습니다.\n",id);
		}
		// 게시물 삭제
		else if (cmd.startsWith("article delete")) {
			int id = Integer.parseInt(cmd.split(" ")[2]);
			
			SecSql sql = new SecSql();
			sql.append("DELETE");
			sql.append("FROM article");
			sql.append("WHERE id = ?",id);
			
			int affectedRow = DBUtil.delete(conn, sql);
			
			if(affectedRow == 0) {
				System.out.printf("%d번 게시물이 존재하지 않습니다\n",id);
				return 0;
			}
			
			System.out.printf("%d번 게시물이 삭제되었습니다.\n",id);
			
		}
		
		return 0;
	}

}
