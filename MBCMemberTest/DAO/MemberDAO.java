package MBCMemberTest.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import MBCMemberTest.DTO.MemberDTO;

public class MemberDAO {

	// �ʵ� DAO
	public MemberDTO memberDTO = new MemberDTO();
	public Connection conn = null;
	public Statement stmt = null;
	public PreparedStatement pstmt = null;
	public ResultSet res = null;
	public int re = 0;

	// ������
	public MemberDAO() {

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

	public void insertmember(MemberDTO memberDTO) throws SQLException {

		try {
			String sql = "insert into member (mno, mid, mpw, nickname, email, addr, regidate)"
					+ "values(member_seq.nextval, ?, ?, ?, ?, ?, sysdate)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberDTO.getMid());
			pstmt.setString(2, memberDTO.getMpw());
			pstmt.setString(3, memberDTO.getNickname());
			pstmt.setString(4, memberDTO.getEmail());
			pstmt.setString(5, memberDTO.getAddr());

			re = pstmt.executeUpdate();
			System.out.println("���๮ �׽�Ʈ:" + sql);// ������ ��� �׽�Ʈ
			if (re > 0) {
				System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
				conn.commit();

			} else {
				System.out.println("SQL������: " + re);
				System.out.println("�Է½���");
				conn.rollback();

			}
		} catch (SQLException e) {
			System.out.println("����: insertmember �޼��忡 �������� Ȯ���ϼ���");
			e.printStackTrace();
		} finally {

			pstmt.close();
		}

	}

	public MemberDTO login(MemberDTO memberDTO) throws SQLException {
		MemberDTO session = null;
		try {
			String sql = "select mid, mpw, nickname from member where mid=? and mpw=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberDTO.getMid());
			pstmt.setString(2, memberDTO.getMpw());

			res = pstmt.executeQuery();
			System.out.println("SQL ���: " + sql);
			if (res.next()) {
				session = new MemberDTO();

				session.setMid(res.getString("mid"));
				session.setMpw(res.getString("mpw"));
				session.setNickname(res.getString("nickname"));
				System.out.println("�α��� ����" + session.getNickname() + "�� �ݰ����ϴ�");

			} else {
				System.out.println("�α��� ����: id, pw�� Ȯ�����ּ���");
				conn.rollback();
			}
		} catch (SQLException e) {
			System.out.println("����: login�޼��� �������� Ȯ�����ּ���");
			e.printStackTrace();
		} finally {
			res.close();
			pstmt.close();

		}

		return session;
	}

	public void readone(String password) throws SQLException {

		try {
			String sql = "select mno, mid, mpw, nickname, email, addr, regidate from member where mpw=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);
			res = pstmt.executeQuery();

			if (res.next()) {
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setMno(res.getInt("mno"));
				memberDTO.setMid(res.getString("mid"));
				memberDTO.setMpw(res.getString("mpw"));
				memberDTO.setNickname(res.getString("nickname"));
				memberDTO.setEmail(res.getString("email"));
				memberDTO.setAddr(res.getString("addr"));
				memberDTO.setRegidate(res.getDate("regidate"));

				System.out.println("-----------------------");
				System.out.println("ȸ����ȣ: " + memberDTO.getMno());
				System.out.println("ID: " + memberDTO.getMid());
				System.out.println("PW: " + memberDTO.getMpw());
				System.out.println("�г���: " + memberDTO.getNickname());
				System.out.println("�̸���: " + memberDTO.getEmail());
				System.out.println("�ּ�: " + memberDTO.getAddr());
				System.out.println("������: " + memberDTO.getRegidate());

			}else {
				System.out.println("�ý��� �����Դϴ� �����ڿ��� �����ϼ���");
			}
		} catch (SQLException e) {
			System.out.println("����: readOne�޼��带 Ȯ���ϼ���");
			e.printStackTrace();
		}finally {
			res.close();
			pstmt.close();
		}
	}

	public void modify(String password, Scanner input) throws SQLException {
		
		try {
			MemberDTO memberDTO = new MemberDTO();
			
			System.out.print("������ �г����� �Է��ϼ���: ");
			memberDTO.setNickname(input.next());
			
			System.out.print("������ ��ȣ�� �Է��ϼ���: ");
			memberDTO.setMpw(input.next());
			
			System.out.print("������ �̸����� �Է��ϼ���: ");
			memberDTO.setEmail(input.next());
			
			System.out.print("������ �ּҸ� �Է��ϼ���: ");
			memberDTO.setAddr(input.next());
			
			
			String sql = "update member set nickname=?, mpw=?, email=?, addr=? where mpw=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getNickname());
			pstmt.setString(2, memberDTO.getMpw());
			pstmt.setString(3, memberDTO.getEmail());
			pstmt.setString(4, memberDTO.getAddr());
			pstmt.setString(5, password);
			
			re = pstmt.executeUpdate();
			
			if(re>0) {
				System.out.println("ȸ������ ������ �Ϸ�Ǿ����ϴ�");
				conn.commit();
				
			}else {
				System.out.println("�������� �ٽ� Ȯ�����ּ���");
				conn.rollback();
			}
		} catch (SQLException e) {
			System.out.println("����: modify �޼��带 Ȯ���ϼ���");
			e.printStackTrace();
		}finally {
			pstmt.close();
		}
	}

	public void delete(String password, Scanner input) throws SQLException {
		System.out.println("ȸ��Ż�� �����մϴ�");
		
		try {
			String sql = "delete from member where mpw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);
			
			re = pstmt.executeUpdate();
			
			if(re>0) {
				System.out.println("ȸ��Ż�� �Ϸ�Ǿ����ϴ�");
				conn.commit();
			}else {
				System.out.println("ȸ��Ż�� ������� �ʾҽ��ϴ� �����ڿ��� �����ϼ���");
			}
		} catch (SQLException e) {
			System.out.println("����: delete�޼��带 Ȯ���ϼ���");
			e.printStackTrace();
		}finally {
			pstmt.close();
		}
		
		
	}

	public void readAll() throws SQLException {

		try {
			String sql = "select mno, nickname, mid, mpw, email, addr, regidate from member order by regidate desc";
			
			stmt = conn.createStatement();
			res = stmt.executeQuery(sql);
			
			System.out.println("��ȣ\t �г���\t ID\t PW\t �̸���\t �ּ�\t ������\t ");
			
			while(res.next()) {
				System.out.print(res.getInt("mno")+"\t");
				System.out.print(res.getString("nickname")+"\t");
				System.out.print(res.getString("mid")+"\t");
				System.out.print(res.getString("mpw")+"\t");
				System.out.print(res.getString("email")+"\t");
				System.out.print(res.getString("addr")+"\t");
				System.out.println(res.getDate("regidate")+"\t");
			}
				System.out.println("---------complete----------");
		} catch (SQLException e) {
			System.out.println("readAll �������� �߸��Ǿ����ϴ�");
			e.printStackTrace();
		}finally {
			res.close();
			stmt.close();
		}
	}

}
