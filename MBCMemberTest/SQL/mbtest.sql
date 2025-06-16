drop table  member  ; --테이블 잘 못 만들었을 경우 삭제용

create table member( --테이블 생성
mno number(5) not null,
mid nvarchar2(10) primary key,
mpw nvarchar2(10) not null,
nickname nvarchar2(50) not null,
email nvarchar2(10),
addr nvarchar2(100),
regidate date default sysdate not null
)	;

create sequence member_seq increment by 1 start with 1 nocycle nocache 	; --회원번호 시퀸스 객체
drop sequence  member_seq -- 시퀸스 삭제용
delete from member --데이터 잘 못 입력시 삭제용

--더미데이터
delete from member where mid = 'aaa'  --더미데이터 잘 못 입력시 삭제용

insert into member (mno, mid, mpw, nickname, email, addr, regidate)--관리자용 계정
values ('0000','admin','admin','MANAGER',null,null, sysdate)


insert into member (mno, mid, mpw, nickname, email, addr, regidate)
values(member_seq.nextval, 'aaa', '1234', 'do', null, null, sysdate)

insert into member (mno, mid, mpw, nickname, email, addr, regidate)
values (member_seq.nextval, 'bbb', '2345', 're', 'asdf@qwer', 'BUSAN', sysdate)

insert into member (mno, mid, mpw, nickname, email, addr, regidate)
values (member_seq.nextval, 'ccc', '2345', 'mi', null, 'DAEGU', sysdate)

insert into member (mno, mid, mpw, nickname, email, addr, regidate)
values (member_seq.nextval, 'ddd', '3456', 'fa', 'qwer@asdf', null, sysdate)



select mid, substr(mid,3), mpw, substr(mpw,3), email, substr(email,3), addr,substr(addr,3)from member	;

select mno, rpad(substr(mid,1,2),8,'*'), rpad(substr(mpw,1,2),8,'*'),nickname, rpad(substr(email,1,3),8,'*'), rpad(substr(addr,1,2),5,'*') , regidate from member



select mno,nickname, rpad(substr(mid,1,2),8,'*'), rpad(substr(mpw,1,2),8,'*'), rpad(substr(email,1,3),8,'*'), rpad(substr(addr,1,2),5,'*') , regidate from member
--이걸 어떻게 써야하나요



--테이블 확인용
select*from member


