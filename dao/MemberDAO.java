package mbcboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import mbcboard.dto.MemberDTO;

public class MemberDAO {
	// 게시판 db와 연동 담당
	// jdbc 5단계
	// 1. Connect 객체 사용하여 ojdbc6.jar 생성
	// 2. url,id,pw,sql 쿼리문 작성
	// 3. 쿼리문 실행
	// 4. 쿼리문의 실행 결과 수신
	// 5. 연결종료

	// 필드
	public MemberDTO memberDTO = new MemberDTO();
	public Connection connection = null; // 1단계에서 사용하는 객체
	public Statement statement = null; // 3단계에서 사용하는 객체 (구형),변수 직접처리 '"+name+"'
	public PreparedStatement preparedstatement = null; // 3단계에서 사용하는 객체 (신형), ?(인파라미터)
	public ResultSet resultset = null; // 4단계에서 표형태로 결과받음 (select)
	public int result = 0;

	// 생성자
	public MemberDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "boardtest",
					"boardtest");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 이름이나, ojdbc6.jar파일이 잘못되었습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("URL,ID,PW가 잘못되었습니다 MemberDAO기본 생성자를 확인하세요.");
			e.printStackTrace();
			System.exit(0); // 강제종료
		}

	}// 기본 생성자

	public void insertmember(MemberDTO memberDTO) throws SQLException {

		try {
			String sql = "insert into member(mno, bwriter, id, pw) values(board_seq.nextval, ?, ?, ?)";
			preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, memberDTO.getBwriter());
			preparedstatement.setString(2, memberDTO.getId());
			preparedstatement.setString(3, memberDTO.getPw());
			
			System.out.println(sql);
			
			result = preparedstatement.executeUpdate();

			if (result > 0) {
				System.out.println("회원가입이 완료되었습니다." + memberDTO.getBwriter() + "님 환영합니다.");
				connection.commit();
			} else {
				System.out.println("실행된 쿼리문: " + sql);
				System.out.println("쿼리실행결과: " + result);
				System.out.println("입력실패");
				connection.rollback();
			}
		} catch (SQLException e) {
			System.out.println("예외: insertBoard() 의 쿼리문을 확인하세요");
			e.printStackTrace();
		} finally {
			preparedstatement.close();
		}
	}

	public void readOne(String selectid) throws SQLException {
		// id 열을 통한 회원정보 조회

		try {
			String sql = "select mno, bwriter, id, pw, regidate from member where id=?";
			preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, selectid);

			resultset = preparedstatement.executeQuery();

			if (resultset.next()) {
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setMno(resultset.getInt("mno"));
				memberDTO.setBwriter(resultset.getString("bwriter"));
				memberDTO.setId(resultset.getString("id"));
				memberDTO.setPw(resultset.getString("pw"));
				memberDTO.setRegidate(resultset.getDate("regidate"));
				System.out.println("-------------------------");

				System.out.println("번호: " + memberDTO.getMno());
				System.out.println("이름(별칭): " + memberDTO.getBwriter());
				System.out.println("id: " + memberDTO.getId());
				System.out.println("pw: " + memberDTO.getPw());
				System.out.println("가입일자: " + memberDTO.getRegidate());

			} else {
				System.out.println("해당 회원이 조회되지 않습니다.");
			}
		} catch (SQLException e) {
			System.out.println("예외: readOne메서드를 확인하세요");
			e.printStackTrace();
		} finally {
			resultset.close();
			preparedstatement.close();
		}

	}

	public void modify(String selectid, Scanner inputStr) throws SQLException {
		// id 를 찾아서 회원정보 수정

		MemberDTO memberDTO = new MemberDTO();

		System.out.println("수정할 이름을 입력하세요");
		memberDTO.setBwriter(inputStr.next());

		System.out.println("수정할 pw을 입력하세요");
		memberDTO.setPw(inputStr.next());

		try {
			String sql = "update member set bwriter=?, pw=?, regidate = sysdate where id=?";
			preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, memberDTO.getBwriter());
			preparedstatement.setString(2, memberDTO.getPw());
			preparedstatement.setString(3, selectid);

			result = preparedstatement.executeUpdate();

			if (result > 0) {
				System.out.println(result + "개의 데이터가 수정되었습니다");
				connection.commit();
			} else {
				System.out.println("수정 실패");
				connection.rollback();
			}
		} catch (SQLException e) {
			System.out.println("예외: modify 메서드를 확인하세요");
			e.printStackTrace();
		} finally {
			preparedstatement.close();
		}

	}

	public void selectAll() throws SQLException {

		try {
			String sql = "select mno, bwriter, id, pw, regidate from member order by regidate desc";

			statement = connection.createStatement();
			resultset = statement.executeQuery(sql);

			System.out.println("번호\t 이름\t id\t pw\t 가입일\t");

			while (resultset.next()) {
				System.out.print(resultset.getInt("mno") + "\t");
				System.out.print(resultset.getString("bwriter") + "\t");
				System.out.print(resultset.getString("id") + "\t");
				System.out.print(resultset.getString("pw") + "\t");
				System.out.println(resultset.getDate("regidate") + "\t");
			}
			System.out.println("--------Complete-------");
		} catch (SQLException e) {
			System.out.println("selectAll메서드 쿼리문이 잘못되었습니다.");
			e.printStackTrace();
		} finally {
			resultset.close();
			statement.close();
		}
	}

	public MemberDTO login(MemberDTO memberDTO) throws SQLException {
		MemberDTO session = null;
		
		try {
			String sql = "select id, pw, bwriter from member where id=? and pw=?";

			preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, memberDTO.getId());
			preparedstatement.setString(2, memberDTO.getPw());
			
			resultset = preparedstatement.executeQuery();

			if (resultset.next()) {
				session = new MemberDTO();
				session.setId(resultset.getString("id"));
				session.setPw(resultset.getString("pw"));
				session.setBwriter(resultset.getString("bwriter"));
				System.out.println("로그인 성공"+session.getBwriter()+"님 환영합니다.");				

			} else {
				System.out.println("로그인 실패: id,pw를 확인해주세요");
				connection.rollback();
			
			}
		} catch (SQLException e) {
			System.out.println("예외: login메서드의 쿼리문이 잘못되었습니다.");

			e.printStackTrace();
		} finally {
			resultset.close();
			preparedstatement.close();
		}
		return session;
	
	}

	public void delete(String selectid) throws SQLException {
		
		try {
			String sql = "delete from member where id = ?";
			preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, selectid);
			System.out.println(sql);
			
			result = preparedstatement.executeUpdate();
			
			if(result>0) {
				System.out.println(result+"개의 계정 및 관련 게시물들이 삭제되었습니다");
				connection.commit();
			}else {
				System.out.println("게시물이 삭제되지 않았습니다");
				
			}
		} catch (SQLException e) {
			System.out.println("예외: delete 메서드의 sql문을 확인하세요");
			e.printStackTrace();
		}finally {
			preparedstatement.close();
		}
		
		
	}

}
