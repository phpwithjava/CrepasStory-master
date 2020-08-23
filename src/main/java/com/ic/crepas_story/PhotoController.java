package com.ic.crepas_story;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.PhotoDao;
import dao.PostDao;
import dao.UserDao;
import vo.PhotoVo;

@Controller
public class PhotoController {

	final static String VIEW_PATH = "/WEB-INF/views/";
	
	PhotoDao photo_dao;
	PostDao post_dao;
	UserDao user_dao;
	
	@Autowired
	HttpSession session;

	public PhotoDao getPhoto_dao() {
		return photo_dao;
	}
	public void setPhoto_dao(PhotoDao photo_dao) {
		this.photo_dao = photo_dao;
	}
	public PostDao getPost_dao() {
		return post_dao;
	}
	public void setPost_dao(PostDao post_dao) {
		this.post_dao = post_dao;
	}
	public UserDao getUser_dao() {
		return user_dao;
	}
	public void setUser_dao(UserDao user_dao) {
		this.user_dao = user_dao;
	}
	
	@RequestMapping("/photo_form.do")
	public String info_form(int user_idx, Model model) {

		List<PhotoVo> list = photo_dao.All_List(user_idx);
		model.addAttribute("list", list);
		
		return VIEW_PATH + "photo_form.jsp";
	}
}
