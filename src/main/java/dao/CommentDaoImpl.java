package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.CommentVo;

public class CommentDaoImpl implements CommentDao {

	SqlSession sqlSession;
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<CommentVo> selectList(int post_idx) {
		// TODO Auto-generated method stub
		List<CommentVo> list = sqlSession.selectList("comment_list_condition", post_idx);
		return list;
	}

	@Override
	public int insert(CommentVo vo) {
		// TODO Auto-generated method stub
		int res = sqlSession.insert("comment_insert", vo);
		return res;
	}

	@Override
	public int update(CommentVo vo) {
		// TODO Auto-generated method stub
		int res = sqlSession.update("comment_update", vo);
		return res;
	}
	
	@Override
	public int delete(int comment_idx) {
		// TODO Auto-generated method stub
		int res = sqlSession.delete("comment_delete", comment_idx);
		return res;
	}

}
