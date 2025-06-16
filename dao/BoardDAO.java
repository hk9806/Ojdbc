package mbcboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import mbcboard.dto.BoardDTO;

public class BoardDAO {
	// 게시판 db와 연동 담당
	// jdbc 5단계
	// 1. Connect 객체 사용하여 ojdbc6.jar 생성
	// 2. url,id,pw,sql 쿼리문 작성
	// 3. 쿼리문 실행
	// 4. 쿼리문의 실행 결과 수신
	// 5. 연결종료

	// 필드
	public BoardDTO boardDTO = new BoardDTO();
	public Connection connection = null; // 1단계에서 사용하는 객체
	public Statement statement = null; // 3단계에서 사용하는 객체 (구형),변수 직접처리 '"+name+"'
	public PreparedStatement preparedstatement = null; // 3단계에서 사용하는 객체 (신형), ?(인파라미터)
	public ResultSet resultset = null; // 4단계에서 표형태로 결과받음 (select)
	public int result = 0; // 4단계에서 정수로 결과받음 (insert, update, delete)
	// 1개의 행이 삽입|수정|삭제 되었습니다. ->정상처리 (commit)
	// 0개의 행이 삽입|수정|삭제 되었습니다. ->비정상처리 (Rollback)

	// 생성자
	public BoardDAO() {
		try {// 예외가 발생할 수 있는 실행문
				// 프로그램 강제종료 처리용
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 1단계 ojdbc6.jar호출
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "boardtest", "boardtest");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 이름이나, ojdbc6.jar파일이 잘못되었습니다.");
			e.printStackTrace();
			System.exit(0);
		} catch (SQLException e) {
			System.out.println("URL,ID,PW 가 잘못되었습니다. BoardDAO에 기본 생성자를 확인하세요");
			e.printStackTrace();
			System.exit(0); // 강제 종료
		}
	}// 기본 생성자

	public void selectAll() throws SQLException { // thows SQLException 쿼리문 예외처리
		// SQL사용 전체열람 결과 출력
		try {
			String sql = "select bno, btitle, bwriter, bdate from board order by bdate desc";
			// 데이터베이스에 board 테이블 내용 수신 쿼리문

			statement = connection.createStatement();// 쿼리문 실행 객체
			resultset = statement.executeQuery(sql); // 쿼리문을 실행하여 결과를 표형태로 받음

			System.out.println("번호 \t 제목\t 작성자\t 작성일 \t");
			while (resultset.next()) {
				// 결과 표에 위에서 부터 아래까지 순차적으로 출력
				System.out.print(resultset.getInt("bno") + "\t");
				System.out.print(resultset.getString("btitle") + "\t");
				System.out.print(resultset.getString("bwriter") + "\t");
				System.out.println(resultset.getDate("bdate") + "\t");

			}
			System.out.println("----------출력완료----------");
		} catch (SQLException e) {
			// 오류 발생시 예외처리문
			System.out.println("selectAll 메서드에 쿼리문이 잘못되었습니다.");
			e.printStackTrace();
		} finally {
			// 상시 실행문
			resultset.close();
			statement.close();
			// 열린 객체를 닫아야 다른 메서드도 정상 작동
		}

	}// selectAll

	public void insertboard(BoardDTO boardDTO) throws SQLException {
		// jdbc를 이용 insert 쿼리 처리
		// PrepardStatement 사용
		// 동적 쿼리문 , ?를 사용해서 세터로 입력

		try {
			String sql = "insert into board(bno, btitle, bcontent, bwriter, bdate) values (board_seq.nextval, ?, ?, ?, sysdate)";
			preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, boardDTO.getBtitle());
			preparedstatement.setString(2, boardDTO.getBcontent());
			preparedstatement.setString(3, boardDTO.getBwriter());
			System.out.println("쿼리확인: " + sql); // 쿼리문 테스트

			result = preparedstatement.executeUpdate();// 정수형태로 받음

			if (result > 0) {
				System.out.println(result + "개의 게시물이 등록 되었습니다");
				connection.commit();

			} else {
				System.out.println("쿼리실행결과: " + result);
				System.out.println("입력실패");
				connection.rollback();
			}

		} catch (SQLException e) {
			System.out.println("예외: intserBoard()메서드에 쿼리문을 확인하세요");
			e.printStackTrace();
		} finally {
			// 예외 발생 및 정상 실행 후 무조건 처리되는 실행문
			preparedstatement.close();

		}
	}// insertboard

	public void readOne(String title) throws SQLException {
		// 제목 문자열이 넘어온 것을 select처리하여 출력

		try {
			String sql = "select bno, btitle, bcontent, bwriter, bdate from board where btitle=?";
			preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, title); // service에서 넘어온 찾고싶은 제목이 ?로 넘어감
			resultset = preparedstatement.executeQuery();

			if (resultset.next()) {// 검색 결과 o
				BoardDTO boardDTO = new BoardDTO(); // 빈 객체 생성
				boardDTO.setBno(resultset.getInt("bno"));
				boardDTO.setBtitle(resultset.getString("btitle"));
				boardDTO.setBcontent(resultset.getString("bcontent"));
				boardDTO.setBwriter(resultset.getString("bwriter"));
				boardDTO.setBdate(resultset.getDate("bdate"));
				// 데이터 베이스에 잇는 행을 객체에 넣기 완료

				System.out.println("---------------------------");
				System.out.println("번호: " + boardDTO.getBno());
				System.out.println("제목: " + boardDTO.getBtitle());
				System.out.println("내용: " + boardDTO.getBcontent());
				System.out.println("작성자: " + boardDTO.getBwriter());
				System.out.println("작성일: " + boardDTO.getBdate());

			} else { // 검색 결과 x
				System.out.println("해당하는 게시물이 존재하지 않습니다.");
			} // if

		} catch (SQLException e) {
			System.out.println("예외:readOne메서드를 확인하세요");
			e.printStackTrace();
		} finally {
			resultset.close();
			preparedstatement.close();
		}

	}

	public void modify(String title, Scanner inputStr) throws SQLException {
		// 제목을 찾아서 내용을 수정
		BoardDTO boardDTO = new BoardDTO();

		System.out.println("수정할 내용을 입력하세요");
		System.out.print("제목: ");
		boardDTO.setBtitle(inputStr.next());

		Scanner inputLine = new Scanner(System.in);
		System.out.print("내용: ");
		boardDTO.setBcontent(inputLine.nextLine());

		try {
			String sql = "update board set btitle=? , bcontent=? ,bdate= sysdate where btitle=?";
			preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setString(1, boardDTO.getBtitle());
			preparedstatement.setString(2, boardDTO.getBcontent());
			preparedstatement.setString(3, title);

			result = preparedstatement.executeUpdate();// 쿼리문 실행결과 정수로 보냄

			if (result > 0) {
				System.out.println(result + "개의 데이터가 수정되었습니다");
				connection.commit();// 영구저장
			} else {
				System.out.println("오류: 수정이 되지 않았습니다.");
				connection.rollback();
			}

		} catch (SQLException e) {
			System.out.println("예외: modify()메서드와 sql문을 확인하세요.");
			e.printStackTrace();
		} finally {
			preparedstatement.close();
		}

	}

	public void deleteOne(int selectbno) throws SQLException {
		// 서비스에서 받은 게시글 번호를 이용하여 데이터 삭제

		try {
			String sql = "delete from board where bno = ?";
			preparedstatement = connection.prepareStatement(sql);
			preparedstatement.setInt(1, selectbno);

			result = preparedstatement.executeUpdate();

			if (result > 0) {
				System.out.println(result + "개의 데이터가 삭제되었습니다");
				connection.commit();
			} else {
				System.out.println("게시물이 삭제되지 않았습니다.");
				connection.rollback();

			}
			System.out.println("------------------------------");
			selectAll(); // 삭제 후 전체 리스트 보기

		} catch (SQLException e) {
			System.out.println("예외: deleteOne 메서드와 SQL문을 확인하세요");
			e.printStackTrace();
		} finally {
			preparedstatement.close();

		}

	}

}
