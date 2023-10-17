package com.koreaIT.example.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.example.JAM.Member;
import com.koreaIT.example.JAM.service.MemberService;
import com.koreaIT.example.JAM.session.Session;


public class MemberController extends Controller{
	
	private MemberService memberService;
	private Scanner sc;
	
	public MemberController(Connection conn, Scanner sc) {
		memberService = new MemberService(conn);
		this.sc = sc;
	}
	
	public void doJoin() {
		/** 멤버 회원가입 */
		System.out.println("== 회원 가입 ==");
		String loginId = null;
		String loginPw = null;
		String name = null;
		
		while(true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();
			if(loginId.length() == 0) {
				System.out.println("로그인 아이디를 입력해주세요.");
				continue;
			}
			
			boolean isLoginDup = memberService.isLoginDup(loginId);
			
			if(isLoginDup) {
				System.out.printf("%s 는(은) 이미 사용중인 아이디 입니다.\n",loginId);
				continue;
			}
			break;
		}
		
		while(true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			if(loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}
			System.out.print("비밀번호 확인 : ");
			String pwCHK = sc.nextLine().trim();
			if(loginPw.equals(pwCHK) == false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			break;
		}
		
		while(true) {
			System.out.print("성함 : ");
			name = sc.nextLine().trim();
			if(name.length() == 0) {
				System.out.println("성함을 입력해주세요.");
				continue;
			}
			break;
		}
		
		memberService.doJoin(loginId, loginPw, name);
		
		System.out.println("회원가입이 완료되었습니다.");
		System.out.printf("[%s] 계정이 생성되었습니다.\n", loginId);
	}
	
	/** 멤버 로그인 */
	public void doLogin() {
		
		if(Session.isLogined()) {
			System.out.println("로그아웃 후 사용해주세요");
			return;
		}
		
		System.out.println("== 로그인 ==");
		
		Member loginMember = null;
		String loginId = null;
		String loginPw = null;
		
		while(true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();
			if(loginId.length() == 0) {
				System.out.println("로그인 아이디를 입력해주세요.");
				continue;
			}
			break;
		}
		
		while(true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			if(loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}
			break;
		}
		loginMember = memberService.doLogin(loginId, loginPw);
		
		if(loginMember == null) {
			System.out.println("아이디와 비밀번호를 확인해주세요.");
			return;
		}
		
		Session.login(loginMember);  
		System.out.printf("%s님 환영합니다.\n", loginId);
	}

	public void doLogout() {
		
		if(Session.isLogined() == false) {
			System.out.println("로그인 후 사용해주세요");
			return;
		}
		
		Session.logout();
		System.out.println("로그아웃 되었습니다.");
	}

	public void showProfile() {
		
		if(Session.isLogined() == false) {
			System.out.println("로그인 후 사용해주세요");
			return;
		}
		
		System.out.println("== 마이 페이지 ==");
		System.out.println("생성일 : " + Session.loginedMember.regDate);
		System.out.println("아이디 : " + Session.loginedMember.loginId);
		System.out.println("이름 : "+ Session.loginedMember.name);
	}
}
