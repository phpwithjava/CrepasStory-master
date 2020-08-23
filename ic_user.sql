
create sequence seq_user_idx;

create table ic_user (
	user_idx int,							-- 유저 일련번호 primary key
	username varchar2(100) not null,				-- 유저명
	userid varchar2(255) unique not null,				-- 유저 아이디
	password varchar2(255) not null,				-- 패스워드
	birthday varchar2(100),						-- 사용자 생일
	phonenumber varchar2(100) not null,				-- 핸드폰번호
	addr varchar2(200) not null,					-- 주소
	profile varchar2(100) null					-- 프로필
)

alter table ic_user add constraint pk_user_idx primary key(user_idx)

-- sample
insert into ic_user values(seq_user_idx.nextVal,'이다운','test','test','90','111','경기','남','a');
insert into ic_user values(seq_user_idx.nextVal,'박성수','test2','test','97','111','경기','남','a');

insert into ic_user values(seq_user_idx.nextVal,'최승용','test3','test','97','111','경기','남','a');
insert into ic_user values(seq_user_idx.nextVal,'일길동','test4','test','97','111','경기','남','a');
insert into ic_user values(seq_user_idx.nextVal,'이길동','test5','test','97','111','경기','남','a');
insert into ic_user values(seq_user_idx.nextVal,'삼길동','test6','test','97','111','경기','남','a');


select * from ic_user

drop table ic_user
drop sequence seq_user_idx
