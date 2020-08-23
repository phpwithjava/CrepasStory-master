-- 일련번호
create sequence seq_comment_idx

create table ic_comment(
	comment_idx int,				-- 댓글 일련번호
	post_idx int,					-- 해당 댓글이 달리는 포스트 일련번호
	user_idx int,					-- 댓글쓴이 일련번호							
	username varchar2(100),				-- 댓글쓴이 이름(user table)
	content varchar2(255) not null,			-- 댓글 내용
	regdate date					-- 댓글 등록 일자
)

--기본키설정
alter table ic_comment add constraint pk_comment_cidx primary key(comment_idx)

--외래키 설정
alter table ic_comment add constraint fk_comment_pidx foreign key(post_idx) references ic_post(post_idx) on delete cascade
alter table ic_comment add constraint fk_comment_uidx foreign key(user_idx) references ic_user(user_idx) on delete cascade

--sample
insert into ic_comment values(
	seq_comment_idx.nextVal,
	20,
	1,
	'이다운',
	'안뇽',
	sysdate
)


select * from ic_comment
drop table ic_comment
drop sequence seq_comment_idx











