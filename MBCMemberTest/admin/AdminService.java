package MBCMemberTest.admin;

import java.sql.SQLException;
import java.util.Scanner;

import MBCMemberTest.DAO.AdminDAO;
import MBCMemberTest.DAO.MemberDAO;
import MBCMemberTest.DTO.MemberDTO;

public class AdminService {

	public MemberDAO memberDAO = new MemberDAO();
	public AdminDAO adminDAO = new AdminDAO();
	public MemberDTO memberDTO = new MemberDTO();

	public void subMenu(Scanner input) throws SQLException {
		boolean run = true;
		while (run) {
			System.out.println("관리자메뉴");

			System.out.println("1.전체회원조회");
			System.out.println("2.회원정보 삭제");
			System.out.println("0.나가기");
			String select = input.next();

			switch (select) {
			case "1":
				System.out.println("전체 회원정보");
				memberDAO.readAll();

				break;

			case "2":
				System.out.println("회원정보 강제삭제처리");
				delete(adminDAO, input);
				break;

			case "0":
				System.out.println("메인");
				run = false;
				break;
			}
		}

	}

	private void delete(AdminDAO adminDAO, Scanner input) throws SQLException {
		System.out.println("삭제할 회원번호를 입력하세요");
		String num = input.next();
		adminDAO.delete(num);
		System.out.println("종료");

	}

}
