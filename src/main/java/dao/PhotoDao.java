package dao;

import java.util.List;

import vo.PhotoVo;

public interface PhotoDao {

	// ����ȭ�� �Խù��� ���� �̹��� ����Ʈ
	List<PhotoVo> selectList(int post_idx);
	// ���� �̹��� ��ƺ��� ����Ʈ
	List<PhotoVo> All_List(int user_idx);
	
	// post_idx�� �������� �������� (�����Ҷ� ���)
	PhotoVo selectOne(int post_idx);
	
	// �̹��� ���ε�
	int Insert(PhotoVo vo);
	
	// �̹��� ����
	int delete(int post_idx);
}
