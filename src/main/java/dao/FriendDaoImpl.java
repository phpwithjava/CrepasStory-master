package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.FriendVo;

public class FriendDaoImpl implements FriendDao {
	
	SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<FriendVo> selectList(int user_idx) {
		// TODO Auto-generated method stub
		List<FriendVo> list = sqlSession.selectList("friend_list", user_idx);
		return list;
	}

	@Override
	public int insert(FriendVo vo) {
		// TODO Auto-generated method stub
		int res = sqlSession.insert("friend_insert", vo);
		return res;
	}

	@Override
	public int update(FriendVo vo) {
		// TODO Auto-generated method stub
		int res = sqlSession.update("friend_update", vo);
		return res;
	}

	@Override
	public int delete(FriendVo vo) {
		// TODO Auto-generated method stub
		int res = sqlSession.delete("friend_delete", vo);
		return res;
	}

}
