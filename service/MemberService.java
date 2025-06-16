package mbcboard.service;

import java.sql.SQLException;
import java.util.Scanner;

import mbcboard.dao.MemberDAO;
import mbcboard.dto.MemberDTO;

public class MemberService {
	// dao, dto 사용하여 부메뉴와 CRUD 처리

	// 필드
	public MemberDAO memberDAO = new MemberDAO();

	public MemberDTO subMenu(Scanner inputStr, MemberDTO session) throws SQLException {
		boolean run = true;
		while (run) {
			System.out.println("게시판 회원메뉴");
			System.out.println("1.회원가입");
			System.out.println("2.로그인");
			System.out.println("3.회원정보 조회");
			System.out.println("4.회원정보 수정");
			System.out.println("5.회원정보 삭제");
			System.out.println("0.나가기");
			String select = inputStr.next();

			switch (select) {

			case "1":
				System.out.println("회원가입 메뉴");
				insertmember(memberDAO, inputStr);
				break;

			case "2":
				System.out.println("로그인 메뉴");
				session = login(memberDAO, inputStr);
				break;

			case "3":
				System.out.println("회원정보 조회메뉴");
				readOne(memberDAO, inputStr);
				break;

			case "4":
				System.out.println("회원정보 수정메뉴");
				modify(memberDAO, inputStr);
				break;

			case "5":
				System.out.println("회원 탈퇴 메뉴");
				delete(memberDAO, inputStr);
				break;

			case "9999":
				System.out.println("전체회원조회");// 관리자용 히든
				readAll();
				break;

			case "0":
				System.out.println("나가기");
				run = false;
				break;

			}// switch
		} // while
		return session;

	}

	private void delete(MemberDAO memberDAO, Scanner inputStr) throws SQLException {
		System.out.println("삭제하려는 회원id를 입력하세요");
		System.out.println(">>>");
		String selectid = inputStr.next();
		memberDAO.delete(selectid);
		System.out.println("-------------종료--------------");
		
	}

	private MemberDTO login(MemberDAO memberDAO, Scanner inputStr) throws SQLException {
		MemberDTO session = new MemberDTO();
		System.out.println("id를 입력하세요");
		session.setId(inputStr.next());

		System.out.println("pw를 입력하세요");
		session.setPw(inputStr.next());

		return 	memberDAO.login(session);
		
	}

	private void readAll() throws SQLException {

		System.out.println("----------- 가입 회원 현황-----------");

		memberDAO.selectAll();
		System.out.println("---------------------------------");

	}

	private void modify(MemberDAO memberDAO, Scanner inputStr) throws SQLException {
		// id열을 이용한 회원정보 수정
		System.out.println("수정할 회원의 id를 입력하세요");
		String selectid = inputStr.next();

		memberDAO.modify(selectid, inputStr);
		System.out.println("-------------종료--------------");
	}

	private void readOne(MemberDAO memberDAO, Scanner inputStr) throws SQLException {
		// id 열을 통해 select처리하여 출력

		System.out.println("정보조회할 id를 입력하세요");
		String selectid = inputStr.next();
		memberDAO.readOne(selectid);
		System.out.println("-------------Complete--------------");

	}

	private void insertmember(MemberDAO memberDAO, Scanner inputStr) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();

		System.out.print("사용하실 이름을 입력하세요: ");
		memberDTO.setBwriter(inputStr.next());

		System.out.print("사용하실 id를 입력하세요: ");
		memberDTO.setId(inputStr.next());

		System.out.print("사용하실 pw를 입력하세요: ");
		memberDTO.setPw(inputStr.next());

		memberDAO.insertmember(memberDTO);

		System.out.println("----------------Complete---------------");
	}
}//class
