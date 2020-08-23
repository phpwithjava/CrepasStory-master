
create sequence seq_photo_idx;

-- 기존 DB
/*create table ic_photo (
	photo_idx int,							-- 사진 일련번호
	user_idx int,							-- 사진업로드 유저
	photoname varchar2(100),					-- 사진 이름
	photo varchar2(255),						-- 이미지
	photosize varchar2(255),					-- 사진 사이즈
	regdate date							-- 업로드 날짜
)*/

-- 수정 DB (photo, photosize 삭제 / post_idx 추가)
create table ic_photo (
	photo_idx int,							-- photo 일련번호
	user_idx int,							-- user 일련번호 (외래키)
	post_idx int,							-- post 일련번호 (외래키)
	photoname varchar2(100),					-- 업로드한 이미지 저장
	regdate date							-- 업로드 날짜
)

alter table ic_photo add constraint pk_photo_idx primary key(photo_idx)

-- 외래키 설정 (foreign)
	-- user_idx 정보 외래키 설정
alter table ic_photo add constraint fk_photo_uidx foreign key(user_idx)
	references ic_user(user_idx) on delete cascade

	-- post_idx 정보 외래키 설정
alter table ic_photo add constraint fk_photo_pidx foreign key(post_idx)
	references ic_post(post_idx) on delete cascade
	
select * from IC_PHOTO
drop table ic_photo
drop sequence seq_photo_idx
