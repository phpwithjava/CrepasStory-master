
create sequence seq_friend_idx;

create table ic_friend (
	f_idx int,		 		-- 친구 일련번호
	user_idx int,				-- 내 일련번호
	friend_idx int,				-- 친구 일련번호
	state int,				-- 상태 (0=요청, 1=거절, 2=수락, 3=수신)
	regdate date				-- 등록 날짜
)

alter table ic_friend add constraint pk_friend_idx primary key(f_idx)

-- 외래키 설정 (foreign)
alter table ic_friend add constraint fk_user_idx foreign key(user_idx)
	references ic_user(user_idx) on delete cascade
	
alter table ic_friend add constraint fk_friend_idx foreign key(friend_idx)
	references ic_user(user_idx) on delete cascade
	
-- sample
-- 1(이다운) => 21(박성수)친구 
insert into ic_friend values(seq_friend_idx.nextVal,
							1,
							42,
							2,
							sysdate)
-- 21(박성수) => 1(이다운)친구
insert into ic_friend values(seq_friend_idx.nextVal,
							42,
							1,
							2,
							sysdate)

							
-- 1(이다운) => 41(최승용) 친구
insert into ic_friend values(seq_friend_idx.nextVal,
							1,
							41,
							2,
							sysdate)
							
-- 41(최승용) => 1(이다운) 친구
insert into ic_friend values(seq_friend_idx.nextVal,
							41,
							1,
							2,
							sysdate)
							
							
							
							
							

delete from ic_friend where f_idx =3							
							
	
select * from ic_friend

drop table ic_friend
drop sequence seq_follow_idx
