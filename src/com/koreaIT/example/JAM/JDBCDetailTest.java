package com.koreaIT.example.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDetailTest {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	JDBCDetailTest(int id) {
		
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
			while(rs.next()) {
				System.out.println("id : " + rs.getInt(1));
				System.out.println("작성일 : " + rs.getString(2));
				System.out.println("수정일 : " + rs.getString(3));
				System.out.println("제목 : " + rs.getString(4));
				System.out.println("내용 : " + rs.getString(5));
				System.out.println("-------------------------------");
			}
			
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
