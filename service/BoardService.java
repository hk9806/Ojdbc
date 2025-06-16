package mbcboard.service;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dao.BoardDAO;
import mbcboard.dto.BoardDTO;
import mbcboard.dto.MemberDTO;

public class BoardService {
	// dao, dto 사용하여 부메뉴와 CRUD 처리

	// 필드
	public BoardDAO boardDAO = new BoardDAO();

	// 생성자

	// 메서드 (부메뉴, 생성, 전체열람, 선택열람, 수정, 삭제)
	public void subMenu(Scanner inputStr, MemberDTO session) throws SQLException {
		boolean run = true;
		while (run) {
			System.out.println("MBC아카데미 게시판 서비스");
			System.out.println("1. 전체열람 ");
			System.out.println("2. 게시글 작성 ");
			System.out.println("3. 게시글 선택열람 ");
			System.out.println("4. 게시글 수정 ");
			System.out.println("5. 게시글 삭제 ");
			System.out.println("0. 나가기");
			String select = inputStr.next();

			switch (select) {
			case "1":
				System.out.println("게시물 전체 열람");
				selectAll(boardDAO);
				break;

			case "2":
				System.out.println("게시글 작성");
				insertboard(boardDAO, inputStr, session);
				break;

			case "3":
				System.out.println("게시글 선택열람");
				readOne(inputStr);
				break;

			case "4":
				System.out.println("게시글 수정");
				modify(inputStr);
				break;

			case "5":
				System.out.println("게시글 삭제");
				deleteOne();
				break;

			case "0":
				boardDAO.connection.close();
				System.out.println("게시글 메뉴 종료");
				run = false;
				break;

			}// switch

		} // while

	}// subMenu

	private void deleteOne() throws SQLException {
		// 번호를 찾아서 삭제
		System.out.println("삭제하려는 게시글 번호를 입력하세요");
		Scanner inputint =new Scanner(System.in);
		System.out.println(">>>");
		int selectbno = inputint.nextInt();
		boardDAO.deleteOne(selectbno);
		System.out.println("-------------종료--------------");
	}

	private void modify(Scanner inputStr) throws SQLException {
		// 제목을 찾아서 내용을 수정
		System.out.println("수정할 게시글의 제목을 입력하세요");
		String title = inputStr.next();
		
		boardDAO.modify(title, inputStr);
		System.out.println("-------------종료--------------");
	}//modify

	private void readOne(Scanner inputStr) throws SQLException {
		// 제목을 입력하면 내용이 보이도록 select 처리
		System.out.println("보고싶은 게시글의 제목을 입력하세요");
		String title = inputStr.next();
		boardDAO.readOne(title);
		System.out.println("-------------종료--------------");
		
		
	}//readone

	private void insertboard(BoardDAO boardDAO, Scanner inputStr, MemberDTO session) throws SQLException {
		//키보드로 입력한 데이터를 DTO를 이용하여 데이터베이스에 insert
		BoardDTO boardDTO = new BoardDTO();

		boardDTO.setBwriter(session.getId());
		System.out.println(boardDTO.getBwriter());
		System.out.print("제목: ");
		boardDTO.setBtitle(inputStr.next());
		
		Scanner inputLine = new Scanner(System.in);
		System.out.print("내용: ");
		boardDTO.setBcontent(inputLine.nextLine()); // nextLine - 띄어쓰기가 있는 문장일때
		
		boardDAO.insertboard(boardDTO); //위에서 만든 객체를 DAO에게 전달
		System.out.println("------------insertBoard------------");
		
	}//insertBoard

	private void selectAll(BoardDAO boardDAO) throws SQLException {
		//dao에게 전체보기 하는 서비스 제공
		System.out.println("------------------------------------");
		System.out.println("----------- mbc 게시판목록 -----------");
		System.out.println("------------------------------------");
		boardDAO.selectAll();
		System.out.println("------------------------------------");
		
	}//selectAll

}// class
