create user mbtest identified by mbtest		; --��������

grant resource, connect to mbtest	;	--���Ѻο�

alter user mbtest default tablespace users 	;	

alter user mbtest temporary tablespace temp 	;