create user mbtest identified by mbtest		; --유저생성

grant resource, connect to mbtest	;	--권한부여

alter user mbtest default tablespace users 	;	

alter user mbtest temporary tablespace temp 	;