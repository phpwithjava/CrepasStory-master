package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import vo.PostVo;

@SuppressWarnings("rawtypes")
public class PostDaoImpl implements PostDao{
	
	SqlSession sqlSession;
	
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<PostVo> selectList(int user_idx) {
		// TODO Auto-generated method stub
		List<PostVo> list = sqlSession.selectList("post_list", user_idx);
		return list;
	}
	
	@Override
	public List<PostVo> selectList(Map pageMap) {
		// TODO Auto-generated method stub
		List<PostVo> list = sqlSession.selectList("post_list_condition", pageMap);
		return list;
	}

	@Override
	public int Insert(PostVo vo) {
		// TODO Auto-generated method stub
		int res = sqlSession.insert("post_insert", vo);
		return res;
	}
	
	@Override
	public int update(PostVo vo) {
		// TODO Auto-generated method stub
		int res = sqlSession.update("post_update", vo);
		return res;
	}

	@Override
	public int delete(int post_idx) {
		// TODO Auto-generated method stub
		int res = sqlSession.delete("post_delete", post_idx);
		return res;
	}

	@Override
	public int selectOne_post(int user_idx) {
		// TODO Auto-generated method stub
		
		// post_idx 제일 높은 값 가져오기
		int res = sqlSession.selectOne("post_idx_list", user_idx);
		return res;
	}

	


}
