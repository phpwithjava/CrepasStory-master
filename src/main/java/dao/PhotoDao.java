package dao;

import java.util.List;

import vo.PhotoVo;

public interface PhotoDao {

	// 메인화면 게시물의 사진 이미지 리스트
	List<PhotoVo> selectList(int post_idx);
	// 사진 이미지 모아보기 리스트
	List<PhotoVo> All_List(int user_idx);
	
	// post_idx로 사진정보 가져오기 (삭제할때 사용)
	PhotoVo selectOne(int post_idx);
	
	// 이미지 업로드
	int Insert(PhotoVo vo);
	
	// 이미지 삭제
	int delete(int post_idx);
}
