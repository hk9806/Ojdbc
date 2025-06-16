package MBCMemberTest.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import MBCMemberTest.DTO.MemberDTO;

public class AdminDAO {

	// �ʵ� DAO
	public MemberDAO memberDAO = new MemberDAO();
	public MemberDTO memberDTO = new MemberDTO();
	public Connection conn = null;
	public Statement stmt = null;
	public PreparedStatement pstmt = null;
	public ResultSet res = null;
	public int re = 0;

	// ������
	public AdminDAO() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.152:1521:xe", "mbtest", "mbtest");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� �̸��̳�, ojdbc6.jar������ �߸��Ǿ����ϴ�.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("URL, ID, PW�� �߸��Ǿ����ϴ� MemberDAO �⺻ �����ڸ� Ȯ���ϼ���.");
			e.printStackTrace();
			System.exit(0);
		}

	}

	public void delete(String num) throws SQLException {

		try {
			String sql = "delete from member where mno = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);

			re = pstmt.executeUpdate();

			if (re > 0) {
				System.out.println("�������� �Ϸ�");
				conn.commit();
				memberDAO.readAll();
			} else {
				System.out.println("��������");
			}
		} catch (SQLException e) {
			System.out.println("����: �޼��带 Ȯ���ϼ���");

			e.printStackTrace();
		} finally {
			pstmt.close();

		}

	}

}
