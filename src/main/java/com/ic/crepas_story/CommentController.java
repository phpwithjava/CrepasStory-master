package com.ic.crepas_story;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.CommentDao;
import dao.UserDao;
import vo.CommentVo;
import vo.UserVo;

@Controller
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class CommentController {

	CommentDao comment_dao;
	UserDao user_dao;

	public UserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(UserDao user_dao) {
		this.user_dao = user_dao;
	}

	public CommentDao getComment_dao() {
		return comment_dao;
	}

	public void setComment_dao(CommentDao comment_dao) {
		this.comment_dao = comment_dao;
	}
	
	@RequestMapping(value="/comment_list.do", method=RequestMethod.GET)
	@ResponseBody
	public List comment_list_json(int post_idx){
		// 게시글 별 댓글 불러오기
		List<CommentVo> list = comment_dao.selectList(post_idx);
		for(CommentVo comment : list) {
			UserVo user = user_dao.selectOne(comment.getUser_idx());
			comment.setUser(user);
		}
		return list;
	}
	
	@RequestMapping(value="/comment_insert.do", method=RequestMethod.GET)
	@ResponseBody
	public Map comment_insert(CommentVo vo){

		// 댓글 입력
		int res = comment_dao.insert(vo);
		
		// 업데이트 된 댓글 DB에서 불러와서 업데이트
		List<CommentVo> list = comment_dao.selectList(vo.getPost_idx());
		for(CommentVo comment : list) {
			// 댓글쓴이 유저정보도 Vo에 추가해서 전달
			UserVo user = user_dao.selectOne(comment.getUser_idx());
			comment.setUser(user);
		}
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("post_idx", vo.getPost_idx());
		
		return map;
		
	}
	
	@RequestMapping(value="/comment_update.do", method=RequestMethod.GET)
	@ResponseBody
	public Map comment_update(CommentVo vo){

		int res = comment_dao.update(vo);
		
		List<CommentVo> list = comment_dao.selectList(vo.getPost_idx());
		for(CommentVo comment : list) {
			UserVo user = user_dao.selectOne(comment.getUser_idx());
			comment.setUser(user);
		}
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("post_idx", vo.getPost_idx());
		
		return map;
		
	}
	
	@RequestMapping(value="/comment_delete.do", method=RequestMethod.GET)
	@ResponseBody
	public Map comment_delete(CommentVo vo){
		
		int res = comment_dao.delete(vo.getComment_idx());
		
		List<CommentVo> list = comment_dao.selectList(vo.getPost_idx());
		for(CommentVo comment : list) {
			UserVo user = user_dao.selectOne(comment.getUser_idx());
			comment.setUser(user);
		}
		
		Map map = new HashMap();
		map.put("list", list);
		map.put("post_idx", vo.getPost_idx());
		
		return map;
	}
}
