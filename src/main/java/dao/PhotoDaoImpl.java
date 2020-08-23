package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.PhotoVo;

public class PhotoDaoImpl implements PhotoDao {

	SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int Insert(PhotoVo vo) {
		// TODO Auto-generated method stub
		int res = sqlSession.insert("photo_insert", vo);
		
		return res;
	}

	@Override
	public List<PhotoVo> selectList(int post_idx) {
		// TODO Auto-generated method stub
		List<PhotoVo> list = sqlSession.selectList("photo_list", post_idx);
		
		return list;
	}

	@Override
	public int delete(int post_idx) {
		// TODO Auto-generated method stub
		int res = sqlSession.delete("photo_delete", post_idx);
		
		return res;
	}

	@Override
	public PhotoVo selectOne(int post_idx) {
		// TODO Auto-generated method stub
		PhotoVo vo = sqlSession.selectOne("photo_one", post_idx);
		
		return vo;
	}

	@Override
	public List<PhotoVo> All_List(int user_idx) {
		// TODO Auto-generated method stub
		
		// 사진 모아보기
		List<PhotoVo> list = sqlSession.selectList("all_list", user_idx);
		return list;
	}

	
	
	
}
