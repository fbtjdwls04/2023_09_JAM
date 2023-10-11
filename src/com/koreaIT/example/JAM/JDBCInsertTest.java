package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCInsertTest {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	JDBCInsertTest(Scanner sc) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");
			
			String title;
			String body;
			while (true) {
				System.out.print("제목 : ");
				title = sc.nextLine().trim();
				if (title.length() == 0) {
					System.out.println("제목을 입력해주세요.");
					continue;
				}
				break;
			}
			while (true) {
				System.out.print("내용 : ");
				body = sc.nextLine().trim();
				if (body.length() == 0) {
					System.out.println("내용을 입력해주세요.");
					continue;
				}
				break;
			}
			
			String sql = "INSERT INTO article "
					+ "SET regDate = NOW(),"
					+ "updateDate = NOW(),"
					+ "title = CONCAT('"+ title +"'),"
					+ "`body` = CONCAT('"+ body +"');";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			sql = "SELECT MAX(id) AS id FROM article";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "번 글이 생성되었습니다.");
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
