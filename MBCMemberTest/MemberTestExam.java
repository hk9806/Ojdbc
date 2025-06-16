package MBCMemberTest;

import java.sql.SQLException;
import java.util.Scanner;

import MBCMemberTest.DAO.MemberDAO;
import MBCMemberTest.DTO.MemberDTO;
import MBCMemberTest.admin.AdminService;

public class MemberTestExam {
	public static Scanner input = new Scanner(System.in);

	public static MemberDAO memberDAO = new MemberDAO();

	public static MemberDTO session = null;

	public static void main(String[] args) throws SQLException {
		// 회원관리 기능 구현

		boolean run = true;
		while (run) {
			System.out.println("--회원가입 TEST--");
			System.out.println("1.회원가입 |2. 로그인 | 3.회원정보");
			System.out.println("4.회원수정 |5. 회원탈퇴| 0.종료");
			String select = input.next();

			switch (select) {
			case "1":
				System.out.println("회원가입 메뉴");
				insertMember(memberDAO, input);
				break;

			case "2":
				System.out.println("로그인");
				session = login(memberDAO, input);
				break;

			case "3":
				if (session != null) {
					System.out.println("회원정보 조회");
					readOne(memberDAO, input);
				} else {
					System.out.println("로그인 후 이용 가능합니다");
					session = login(memberDAO, input);
				}
				break;

			case "4":
				if (session != null) {
					System.out.println("회원수정");
					modify(memberDAO, input);
				} else {
					System.out.println("로그인 후 이용 가능합니다");
					session = login(memberDAO, input);
				}
				break;

			case "5":
				if (session != null) {
					System.out.println("회원탈퇴");
					delete(memberDAO, input);
				} else {
					System.out.println("로그인 후 이용 가능합니다");
					session = login(memberDAO, input);
				}
				break;
	
			case "1999":
				if (session != null && session.getMid().equals("admin")) {
					System.out.println(session.getMid() + "님 관리자 권한메뉴입니다");
					AdminService adminsv = new AdminService();
					adminsv.subMenu(input);

				} else {
					System.out.println("0~5사이 값만 입력하세요");
				}
				break;

			case "0":
				System.out.println("프로그램 종료");
				run = false;
				break;

			default:
				System.out.println("0~5사이 값만 입력하세요");

			}

		} // switch

	}// while

	
	private static void delete(MemberDAO memberDAO, Scanner input) throws SQLException {
		System.out.println("비밀번호를 입력하세요");
		String password = input.next();

		memberDAO.delete(password, input);
		System.out.println("------------Complete------------");

	}

	private static void modify(MemberDAO memberDAO, Scanner input) throws SQLException {
		System.out.println("비밀번호를 입력하세요");
		String password = input.next();

		memberDAO.modify(password, input);
		System.out.println("------------Complete------------");

	}

	private static void readOne(MemberDAO memberDAO, Scanner input) throws SQLException {
		System.out.println("현재 계정 상세정보입니다");

		System.out.println("비밀번호를 입력하세요");
		String password = input.next();
		memberDAO.readone(password);
		System.out.println("------------Complete------------");
	}

	private static MemberDTO login(MemberDAO memberDAO, Scanner input) throws SQLException {
		MemberDTO session = new MemberDTO();
		System.out.println("로그인 화면입니다");
		System.out.print("id: ");
		session.setMid(input.next());

		System.out.print("pw: ");
		session.setMpw(input.next());

		return memberDAO.login(session);
	}

	private static void insertMember(MemberDAO memberDAO, Scanner input) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();
		System.out.println("회원가입 메뉴입니다 아래 정보를 순차적으로 입력해주세요");

		System.out.print("ID: ");
		memberDTO.setMid(input.next());

		System.out.print("pw: ");
		memberDTO.setMpw(input.next());

		System.out.print("닉네임: ");
		memberDTO.setNickname(input.next());

		System.out.println("이메일: ");
		memberDTO.setEmail(input.next());

		System.out.println("주소: ");
		memberDTO.setAddr(input.next());

		memberDAO.insertmember(memberDTO);

		System.out.println("------------Complete------------");
	}

}
