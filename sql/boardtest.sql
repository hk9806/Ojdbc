--------------------------member 테이블 부모로 생성--------------------------
delete from member

create table member(
mno number(5) not null,
bwriter nvarchar2(10) not null, 
id nvarchar2(10) primary key, --board 테이블의 id와 fk로 관계설정 (타입 일치)
pw nvarchar2(10) not null,
regidate date default sysdate not null
)

--시퀀스 객체는 이미 1개가 있으니 board_seq를 같이 사용
drop table member --member 테이블 삭제

--부모 더미데이터 입력
insert into member (mno, bwriter, id,  pw)
values(board_seq.nextval, '김기원', 'kkw', '1234')

insert into member (mno, bwriter, id,  pw)
values(board_seq.nextval, '이현우', 'lee', '1234')

insert into member (mno, bwriter, id,  pw)
values(board_seq.nextval, '박채은', 'park', '1234')

insert into member (mno, bwriter, id,  pw)
values(board_seq.nextval, '양지민', 'yang', '1234')

insert into member (mno, bwriter, id,  pw)
values(board_seq.nextval, '홍경훈', 'hong', '1234')

select*from member
delete from member where id = 'yang'
--------------------------member의 자식 board 외래키 생성 필수------------------

drop table board
drop sequence board_seq;

create table board(
bno number(5) primary key,
btitle nvarchar2(30) not null,
bcontent nvarchar2(1000) not null,
bwriter nvarchar2(10) not null,
bdate date not null
)	;

create sequence board_seq increment by 1 start with 1 nocycle nocache	;

alter table board drop constraint board_member_fk	;
alter table board add constraint board_member_fk foreign key (bwriter) references member (id) on delete cascade	;

--board테이블은 member의 자식 테이블로 member에 mname과 board의 bwriter를 관계설정
--ORA-02267: column type incopatible with referenced clumn type ->fk는 pk로만 연동가능
-- pk와 fk를 확인하고 실행 ->자식 테이블에 더미데이터를 삭제하고 실행
-- board에 더미데이터를 입력 후
--ORA-02291: integrity constraint (BOARDTEST.BOARD_MEMBER_FK) violated - parent key not found (0 rows affected)
--부모 테이블에 김기원이라는 값이 없음-> board에 김기원을 kkw로 변경

delete from board --조건 없이 delete를 실행하면 모든 데이터 삭제됨 -> 외래키 다시 지정

insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '덥습니다','무더운데 등교하시느라 고생 하셨습니다','kkw',sysdate)
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '안녕하세요','무더운데 등교하시느라 고생 하셨습니다','lee',sysdate)
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '감사합니다','무더운데 등교하시느라 고생 하셨습니다','park',sysdate)
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '고생하셨습니다','무더운데 등교하시느라 고생 하셨습니다','yang',sysdate)
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '힘내세요','무더운데 등교하시느라 고생 하셨습니다','hong',sysdate)
insert into BOARD (bno, btitle, bcontent, bwriter, bdate)
values(board_seq.nextval, '힘내세요','무더운데 등교하시느라 고생 하셨습니다','bbb',sysdate)


select*from board	;
delete from board
----------------------------------------------------------------------------------
select owner, constraint_name, constraint_type,table_name from user_constraints




--조인: 2개의 테이블을 연결하여 값을 가져옴

select b.*, m.bwriter from member m inner join board b on m.id = b.bwriter where id='kkw'	;

