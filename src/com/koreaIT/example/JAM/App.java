package com.koreaIT.example.JAM;

import java.util.Scanner;

public class App {
	public void start() {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();
			String splitCommand[] = command.split(" ");

			if (command.length() == 0)
				continue;
			if (command.equals("exit"))
				break;
			if (command.startsWith("article") && splitCommand.length > 1) {
				
				// 게시물 작성
				if (splitCommand[1].equals("write")) { 
					new JDBCInsertTest(sc);
				}
				
				// 게시물 목록
 				else if (splitCommand[1].equals("list")) { 
					new JDBCSelectTest();
				}
				
				// 게시물 디테일
 				else if (splitCommand[1].equals("detail")) {
					if (!numberCheck(splitCommand)) {
						continue;
					}
					
					int id = Integer.parseInt(splitCommand[2]);
					new JDBCDetailTest(id);
				}
				
				// 게시물 삭제
 				else if (splitCommand[1].equals("delete")) {
 					if (!numberCheck(splitCommand)) {
						continue;
					}
 					
					int id = Integer.parseInt(splitCommand[2]);
					new JDBCDeleteTest(id);
				}
				
				// 게시물 수정
 				else if (splitCommand[1].equals("modify")) {
 					if (!numberCheck(splitCommand)) {
						continue;
					}
 					
					int id = Integer.parseInt(splitCommand[2]);
					new JDBCUpdateTest(id, sc);
				}
				
				// 잘못된 명령어 처리
 				else {
					System.out.println("존재하지 않는 명령어입니다.");
				}
			}else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}
	
	static boolean numberCheck(String[] splitCommand) {
		if (splitCommand.length < 3) {
			System.out.println("게시물 번호를 입력해주세요.");
			return false;
		}else {
			try {
				Integer.parseInt(splitCommand[2]);
			} catch(NumberFormatException ex) {
				System.out.println("숫자를 입력해주세요.");
				return false;
			}
		}
		return true;
	}
}

