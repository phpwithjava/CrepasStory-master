package dao;

import vo.UserVo;

public interface UserDao {

	//List<UserVo> selectList();

	// userid�� �Խù� 1�� �˻�
	UserVo selectOne(String id);
	// ȸ�� ����
	int insert(UserVo vo);
	// user_idx�� �Խù� 1�� �˻�
	UserVo selectOne(int user_idx);
	// ȸ�� ����
	int update(UserVo vo);

}
