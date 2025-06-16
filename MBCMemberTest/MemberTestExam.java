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
		// ȸ������ ��� ����

		boolean run = true;
		while (run) {
			System.out.println("--ȸ������ TEST--");
			System.out.println("1.ȸ������ |2. �α��� | 3.ȸ������");
			System.out.println("4.ȸ������ |5. ȸ��Ż��| 0.����");
			String select = input.next();

			switch (select) {
			case "1":
				System.out.println("ȸ������ �޴�");
				insertMember(memberDAO, input);
				break;

			case "2":
				System.out.println("�α���");
				session = login(memberDAO, input);
				break;

			case "3":
				if (session != null) {
					System.out.println("ȸ������ ��ȸ");
					readOne(memberDAO, input);
				} else {
					System.out.println("�α��� �� �̿� �����մϴ�");
					session = login(memberDAO, input);
				}
				break;

			case "4":
				if (session != null) {
					System.out.println("ȸ������");
					modify(memberDAO, input);
				} else {
					System.out.println("�α��� �� �̿� �����մϴ�");
					session = login(memberDAO, input);
				}
				break;

			case "5":
				if (session != null) {
					System.out.println("ȸ��Ż��");
					delete(memberDAO, input);
				} else {
					System.out.println("�α��� �� �̿� �����մϴ�");
					session = login(memberDAO, input);
				}
				break;
	
			case "1999":
				if (session != null && session.getMid().equals("admin")) {
					System.out.println(session.getMid() + "�� ������ ���Ѹ޴��Դϴ�");
					AdminService adminsv = new AdminService();
					adminsv.subMenu(input);

				} else {
					System.out.println("0~5���� ���� �Է��ϼ���");
				}
				break;

			case "0":
				System.out.println("���α׷� ����");
				run = false;
				break;

			default:
				System.out.println("0~5���� ���� �Է��ϼ���");

			}

		} // switch

	}// while

	
	private static void delete(MemberDAO memberDAO, Scanner input) throws SQLException {
		System.out.println("��й�ȣ�� �Է��ϼ���");
		String password = input.next();

		memberDAO.delete(password, input);
		System.out.println("------------Complete------------");

	}

	private static void modify(MemberDAO memberDAO, Scanner input) throws SQLException {
		System.out.println("��й�ȣ�� �Է��ϼ���");
		String password = input.next();

		memberDAO.modify(password, input);
		System.out.println("------------Complete------------");

	}

	private static void readOne(MemberDAO memberDAO, Scanner input) throws SQLException {
		System.out.println("���� ���� �������Դϴ�");

		System.out.println("��й�ȣ�� �Է��ϼ���");
		String password = input.next();
		memberDAO.readone(password);
		System.out.println("------------Complete------------");
	}

	private static MemberDTO login(MemberDAO memberDAO, Scanner input) throws SQLException {
		MemberDTO session = new MemberDTO();
		System.out.println("�α��� ȭ���Դϴ�");
		System.out.print("id: ");
		session.setMid(input.next());

		System.out.print("pw: ");
		session.setMpw(input.next());

		return memberDAO.login(session);
	}

	private static void insertMember(MemberDAO memberDAO, Scanner input) throws SQLException {
		MemberDTO memberDTO = new MemberDTO();
		System.out.println("ȸ������ �޴��Դϴ� �Ʒ� ������ ���������� �Է����ּ���");

		System.out.print("ID: ");
		memberDTO.setMid(input.next());

		System.out.print("pw: ");
		memberDTO.setMpw(input.next());

		System.out.print("�г���: ");
		memberDTO.setNickname(input.next());

		System.out.println("�̸���: ");
		memberDTO.setEmail(input.next());

		System.out.println("�ּ�: ");
		memberDTO.setAddr(input.next());

		memberDAO.insertmember(memberDTO);

		System.out.println("------------Complete------------");
	}

}
