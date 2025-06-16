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
			System.out.println("�����ڸ޴�");

			System.out.println("1.��üȸ����ȸ");
			System.out.println("2.ȸ������ ����");
			System.out.println("0.������");
			String select = input.next();

			switch (select) {
			case "1":
				System.out.println("��ü ȸ������");
				memberDAO.readAll();

				break;

			case "2":
				System.out.println("ȸ������ ��������ó��");
				delete(adminDAO, input);
				break;

			case "0":
				System.out.println("����");
				run = false;
				break;
			}
		}

	}

	private void delete(AdminDAO adminDAO, Scanner input) throws SQLException {
		System.out.println("������ ȸ����ȣ�� �Է��ϼ���");
		String num = input.next();
		adminDAO.delete(num);
		System.out.println("����");

	}

}
