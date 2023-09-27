package com.koreaIT.example.JAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("=== program start ===");
		Scanner sc = new Scanner(System.in);
		
		List<Article> articles = new ArrayList<>();
		int lastId = 0;
		
		while(true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();
			if(command.equals("exit")) break;
		}
	}
}
