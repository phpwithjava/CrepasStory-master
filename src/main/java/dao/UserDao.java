package dao;

import vo.UserVo;

public interface UserDao {

	//List<UserVo> selectList();

	// userid로 게시물 1건 검색
	UserVo selectOne(String id);
	// 회원 가입
	int insert(UserVo vo);
	// user_idx로 게시물 1건 검색
	UserVo selectOne(int user_idx);
	// 회원 가입
	int update(UserVo vo);

}
