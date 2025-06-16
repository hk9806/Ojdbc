package mbcboard.dto;

	import java.sql.Date;

	public class MemberDTO {
		
		private	int mno; //회원번호 (시퀸스자동생성)
		private String bwriter; 	//작성자 (멤버에선 닉네임,이름)
		private String id;	
		private	String pw;
		private	Date regidate;	//가입일
		
		
		
		
		
		
		
		
		
		
		
		public int getMno() {
			return mno;
		}
		public String getBwriter() {
			return bwriter;
		}
		public String getId() {
			return id;
		}
		public String getPw() {
			return pw;
		}
		public Date getRegidate() {
			return regidate;
		}
		public void setMno(int mno) {
			this.mno = mno;
		}
		public void setBwriter(String bwriter) {
			this.bwriter = bwriter;
		}
		public void setId(String id) {
			this.id = id;
		}
		public void setPw(String pw) {
			this.pw = pw;
		}
		public void setRegidate(Date regidate) {
			this.regidate = regidate;
		}
		
		
		
		
	}


