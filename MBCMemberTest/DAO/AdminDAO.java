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

	// 필드 DAO
	public MemberDAO memberDAO = new MemberDAO();
	public MemberDTO memberDTO = new MemberDTO();
	public Connection conn = null;
	public Statement stmt = null;
	public PreparedStatement pstmt = null;
	public ResultSet res = null;
	public int re = 0;

	// 생성자
	public AdminDAO() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.152:1521:xe", "mbtest", "mbtest");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 이름이나, ojdbc6.jar파일이 잘못되었습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("URL, ID, PW가 잘못되었습니다 MemberDAO 기본 생성자를 확인하세요.");
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
				System.out.println("계정삭제 완료");
				conn.commit();
				memberDAO.readAll();
			} else {
				System.out.println("삭제실패");
			}
		} catch (SQLException e) {
			System.out.println("예외: 메서드를 확인하세요");

			e.printStackTrace();
		} finally {
			pstmt.close();

		}

	}

}
