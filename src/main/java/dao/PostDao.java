package dao;

import java.util.List;
import java.util.Map;

import vo.PostVo;
@SuppressWarnings("rawtypes")
public interface PostDao {
	
	List<PostVo> selectList(int user_idx);
	
	// post_idx ���� ���� �� ��������
	int selectOne_post(int user_idx);
	
	List<PostVo> selectList(Map map);
	
	int Insert(PostVo vo);
	
	int update(PostVo vo);
	
	int delete(int post_idx);

}
