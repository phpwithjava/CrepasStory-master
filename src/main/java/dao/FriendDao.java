package dao;

import java.util.List;

import vo.FriendVo;

public interface FriendDao {
	
	List<FriendVo> selectList(int user_idx);
	
	int insert(FriendVo vo);
	
	int update(FriendVo vo);
	
	int delete(FriendVo vo);

}
