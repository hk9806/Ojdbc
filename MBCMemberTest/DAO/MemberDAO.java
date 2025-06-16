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

	// 필드 DAO
	public MemberDTO memberDTO = new MemberDTO();
	public Connection conn = null;
	public Statement stmt = null;
	public PreparedStatement pstmt = null;
	public ResultSet res = null;
	public int re = 0;

	// 생성자
	public MemberDAO() {

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
			System.out.println("실행문 테스트:" + sql);// 쿼리문 출력 테스트
			if (re > 0) {
				System.out.println("회원가입이 완료되었습니다.");
				conn.commit();

			} else {
				System.out.println("SQL실행결과: " + re);
				System.out.println("입력실패");
				conn.rollback();

			}
		} catch (SQLException e) {
			System.out.println("예외: insertmember 메서드에 쿼리문을 확인하세요");
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
			System.out.println("SQL 출력: " + sql);
			if (res.next()) {
				session = new MemberDTO();

				session.setMid(res.getString("mid"));
				session.setMpw(res.getString("mpw"));
				session.setNickname(res.getString("nickname"));
				System.out.println("로그인 성공" + session.getNickname() + "님 반갑습니다");

			} else {
				System.out.println("로그인 실패: id, pw를 확인해주세요");
				conn.rollback();
			}
		} catch (SQLException e) {
			System.out.println("예외: login메서드 쿼리문을 확인해주세요");
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
				System.out.println("회원번호: " + memberDTO.getMno());
				System.out.println("ID: " + memberDTO.getMid());
				System.out.println("PW: " + memberDTO.getMpw());
				System.out.println("닉네임: " + memberDTO.getNickname());
				System.out.println("이메일: " + memberDTO.getEmail());
				System.out.println("주소: " + memberDTO.getAddr());
				System.out.println("가입일: " + memberDTO.getRegidate());

			}else {
				System.out.println("시스템 오류입니다 관리자에게 문의하세요");
			}
		} catch (SQLException e) {
			System.out.println("예외: readOne메서드를 확인하세요");
			e.printStackTrace();
		}finally {
			res.close();
			pstmt.close();
		}
	}

	public void modify(String password, Scanner input) throws SQLException {
		
		try {
			MemberDTO memberDTO = new MemberDTO();
			
			System.out.print("변경할 닉네임을 입력하세요: ");
			memberDTO.setNickname(input.next());
			
			System.out.print("변경할 암호를 입력하세요: ");
			memberDTO.setMpw(input.next());
			
			System.out.print("변경할 이메일을 입력하세요: ");
			memberDTO.setEmail(input.next());
			
			System.out.print("변경할 주소를 입력하세요: ");
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
				System.out.println("회원정보 수정이 완료되었습니다");
				conn.commit();
				
			}else {
				System.out.println("수정실패 다시 확인해주세요");
				conn.rollback();
			}
		} catch (SQLException e) {
			System.out.println("예외: modify 메서드를 확인하세요");
			e.printStackTrace();
		}finally {
			pstmt.close();
		}
	}

	public void delete(String password, Scanner input) throws SQLException {
		System.out.println("회원탈퇴를 진행합니다");
		
		try {
			String sql = "delete from member where mpw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, password);
			
			re = pstmt.executeUpdate();
			
			if(re>0) {
				System.out.println("회원탈퇴가 완료되엇습니다");
				conn.commit();
			}else {
				System.out.println("회원탈퇴가 진행되지 않았습니다 관리자에게 문의하세요");
			}
		} catch (SQLException e) {
			System.out.println("예외: delete메서드를 확인하세요");
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
			
			System.out.println("번호\t 닉네임\t ID\t PW\t 이메일\t 주소\t 가입일\t ");
			
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
			System.out.println("readAll 쿼리문이 잘못되었습니다");
			e.printStackTrace();
		}finally {
			res.close();
			stmt.close();
		}
	}

}
