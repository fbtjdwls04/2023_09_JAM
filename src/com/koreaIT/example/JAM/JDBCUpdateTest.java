package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCUpdateTest {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	JDBCUpdateTest(int id, Scanner sc) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");
			
			String sql = "SELECT * FROM article WHERE id = "+ id +";";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(!rs.isBeforeFirst()) {
				System.out.println("게시물이 존재하지 않습니다.");
				return;
			}
			
			String title;
			String body;
			while(true) {
				System.out.print("제목 : ");
				title = sc.nextLine().trim();
				
				if(title.length() == 0) {
					System.out.println("제목을 입력해주세요.");
				}else {
					break;
				}
			}
			while(true) {
				System.out.print("내용 : ");
				body = sc.nextLine().trim();
				
				if(body.length() == 0) {
					System.out.println("내용을 입력해주세요.");
				}else {
					break;
				}
			}
			
			sql = "UPDATE article " 
				+	"SET title = '"+ title +"',"
				+	"`body` = '"+ body + "',"
				+	"updateDate = NOW() "
				+	"WHERE id = "+id+";";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			System.out.println(id+"번 글이 수정되었습니다.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {	
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
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
