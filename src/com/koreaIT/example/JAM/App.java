package com.koreaIT.example.JAM;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
	public void start() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);
		int lastArticleId = 0;
		ArrayList<Article> articles = new ArrayList<>();

		while (true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();
			String splitCommand[] = command.split(" ");

			if (command.length() == 0)
				continue;
			if (command.equals("exit"))
				break;
			if (command.startsWith("article") && splitCommand.length > 1) {
				if (splitCommand[1].equals("write")) { // 게시물 작성
					int id = lastArticleId + 1;
					String title;
					String body;
					while (true) {
						System.out.print("제목 ) ");
						title = sc.nextLine().trim();
						if (title.length() == 0) {
							System.out.println("제목을 입력해주세요.");
							continue;
						}
						break;
					}
					while (true) {
						System.out.print("내용 ) ");
						body = sc.nextLine().trim();
						if (body.length() == 0) {
							System.out.println("내용을 입력해주세요.");
							continue;
						}
						break;
					}
					Article newArticle = new Article(id, title, body);

					articles.add(newArticle);

					System.out.println(id + "번 글이 생성되었습니다.");

					lastArticleId++;

				} else if (splitCommand[1].equals("list")) { // 게시물 목록
					if (articles.size() == 0) {
						System.out.println("게시글이 없습니다.");
					}

					for (int i = articles.size() - 1; i >= 0; i--) {
						System.out.println("-----------------------------");
						System.out.println(articles.get(i).id + "번 글 -");
						System.out.println("제목: " + articles.get(i).title);
						System.out.println("내용: " + articles.get(i).body);
					}
				} else if (splitCommand[1].equals("detail")) {
					if (splitCommand.length < 3) {
						System.out.println("게시물 번호를 입력해주세요.");
						continue;
					}else {
						try {
							Integer.parseInt(splitCommand[2]);
						} catch(NumberFormatException ex) {
							System.out.println("숫자를 입력해주세요.");
							continue;
						}
					}
					int id = Integer.parseInt(splitCommand[2]);
					boolean findIdCheck = false;
					for (int i = 0; i < articles.size(); i++) {
						if (articles.get(i).id == id) {
							System.out.println("-----------------------------");
							System.out.println(id + "번 글 -");
							System.out.println("제목: " + articles.get(i).title);
							System.out.println("내용: " + articles.get(i).body);
							findIdCheck = true;
						}
					}
					if (!findIdCheck) {
						System.out.println(id + "번 글은 존재하지 않습니다.");
					}

				} else if (splitCommand[1].equals("delete")) {
					if (splitCommand.length < 3) {
						System.out.println("게시물 번호를 입력해주세요.");
						continue;
					}else {
						try {
							Integer.parseInt(splitCommand[2]);
						} catch(NumberFormatException ex) {
							System.out.println("숫자를 입력해주세요.");
							continue;
						}
					}
					int id = Integer.parseInt(splitCommand[2]);
					boolean findIdCheck = false;
					for (int i = 0; i < articles.size(); i++) {
						if (articles.get(i).id == id) {
							System.out.println(id + "번 게시물을 삭제하였습니다.");
							articles.remove(i);
							findIdCheck = true;
						}
					}
					if (!findIdCheck) {
						System.out.println(id + "번 글은 존재하지 않습니다.");
					}
				}else {
					System.out.println("존재하지 않는 명령어입니다.");
				}
			}else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		System.out.println("== 프로그램 끝 ==");

		sc.close();
	}
}
class Article {
	int id;
	String title;
	String body;

	Article(int id, String title, String body) {
		this.id = id;
		this.title = title;
		this.body = body;
	}
}
