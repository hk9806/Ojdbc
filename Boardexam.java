package mbcboard;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dto.MemberDTO;
import mbcboard.service.BoardService;
import mbcboard.service.MemberService;


public class Boardexam {
	public static Scanner inputStr = new Scanner(System.in);
	public static MemberDTO session;
	
	public static void main(String[] args) throws SQLException {
		
		boolean run = true;
		while (run) {
			System.out.println("MBC 게시판메뉴");
			System.out.println("1. 회원 | 2.게시판| 0.종료");
			String select = inputStr.next();

			switch (select) {
			case "1":
				System.out.println("회원관리메뉴");
				MemberService memberService = new MemberService();
				session = memberService.subMenu(inputStr, session);
				break;

			case "2":
				if(session !=null) {
				System.out.println("게시판 메뉴");
				BoardService boardService = new BoardService();
				boardService.subMenu(inputStr, session);
				
				}
				else {
					System.out.println("게시판은 로그인 후 사용가능합니다");
				}
				break;
			case"0":
				System.out.println("종료");
				run = false;
				break;
				
			default: 
				System.out.println("0~2사이 값만 입력하세요.");
				break;

			}// switch

		} // while

	}// main

}// class
