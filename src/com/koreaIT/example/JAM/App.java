package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
		ArticleController articleController = new ArticleController(conn, sc, cmd);
		Controller controller = null;
		
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
			articleController.doWrite();
		} 
		// 게시물 목록
		else if (cmd.equals("article list")) {
			articleController.showList();
		} 
		// 게시물 디테일
		else if (cmd.startsWith("article detail")) {
			articleController.showDetail();
		}
		// 게시물 수정
		else if (cmd.startsWith("article modify")) {
			articleController.doModify();
		}
		// 게시물 삭제
		else if (cmd.startsWith("article delete")) {
			articleController.doDelete();
		}else {
			System.out.println("존재하지 않는 명령어입니다.");
		}
		
		return 0;
	}

}
