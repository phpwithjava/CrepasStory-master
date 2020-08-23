package dao;

import org.apache.ibatis.session.SqlSession;

import vo.UserVo;

public class UserDaoImpl implements UserDao{

	SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/*@Override
	public List<UserVo> selectList() {
		// TODO Auto-generated method stub
		List<UserVo> list = null;
		
		list = sqlSession.selectList("post_list");
		
		return list;
	}*/

	@Override
	public UserVo selectOne(String id) {
		// TODO Auto-generated method stub
		UserVo vo = null;
		
		vo = sqlSession.selectOne("login_check", id);
		
		return vo;
	}

	@Override
	public int insert(UserVo vo) {
		// TODO Auto-generated method stub
		int res = 0;
		
		res = sqlSession.insert("member_insert", vo);
		
		return res;
	}

	@Override
	public UserVo selectOne(int user_idx) {
		// TODO Auto-generated method stub
		UserVo vo = null;
		// 회원 정보 보기
		vo = sqlSession.selectOne("info_list", user_idx);
		
		return vo;
	}

	@Override
	public int update(UserVo vo) {
		// TODO Auto-generated method stub
		int res = 0;
		
		res = sqlSession.update("user_update", vo);
		
		return res;
	}
	
}
