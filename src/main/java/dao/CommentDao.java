package dao;

import java.util.List;

import vo.CommentVo;

public interface CommentDao {
	
	List<CommentVo> selectList(int post_idx);
	
	int insert(CommentVo vo);
	int update(CommentVo vo);
	int delete(int comment_idx);
	

}
