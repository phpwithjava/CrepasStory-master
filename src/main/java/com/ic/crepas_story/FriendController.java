package com.ic.crepas_story;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.FriendDao;
import dao.UserDao;
import vo.FriendVo;
import vo.UserVo;

@Controller
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class FriendController {

	final static String VIEW_PATH = "/WEB-INF/views/";
	
	Map map;
	
	UserDao user_dao;
	FriendDao friend_dao;
	
	public FriendDao getFriend_dao() {
		return friend_dao;
	}

	public void setFriend_dao(FriendDao friend_dao) {
		this.friend_dao = friend_dao;
	}

	@Autowired
	HttpSession session;
	public UserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(UserDao user_dao) {
		this.user_dao = user_dao;
	}
	
	@RequestMapping(value="/friend_list.do", method=RequestMethod.GET)
	@ResponseBody
	public List friend_list(){
		UserVo user = (UserVo) session.getAttribute("user");
		List<FriendVo> list = friend_dao.selectList(user.getUser_idx());
		
		return list;
	}
	
	@RequestMapping(value="/check_friend.do", method=RequestMethod.GET)
	@ResponseBody
	public Map check_friend(FriendVo vo){
		
		map = new HashMap();
		List<FriendVo> list = friend_dao.selectList(vo.getUser_idx());
		
		String result = "no";
		for(FriendVo c_vo : list) {
			if(c_vo.getFriend_idx()==vo.getFriend_idx())
				result = "yes";			
		}
		map.put("result", result);
		
		return map;
	}
	
	@RequestMapping(value="/friend_insert.do", method=RequestMethod.GET)
	public String friend_insert(int user_idx, int friend_idx){
		FriendVo vo1 = new FriendVo();
		FriendVo vo2 = new FriendVo();
		
		vo1.setUser_idx(user_idx);
		vo1.setFriend_idx(friend_idx);
		int res = friend_dao.insert(vo1);
		
		vo2.setUser_idx(friend_idx);
		vo2.setFriend_idx(user_idx);
		res = friend_dao.insert(vo2);
		
		return "redirect:index.do";
	}
	
	@RequestMapping(value="/friend_delete.do", method=RequestMethod.GET)
	public String friend_delete(int user_idx, int friend_idx) {
		FriendVo vo1 = new FriendVo();
		FriendVo vo2 = new FriendVo();
		
		vo1.setUser_idx(user_idx);
		vo1.setFriend_idx(friend_idx);
		int res = friend_dao.delete(vo1);
		
		vo2.setUser_idx(friend_idx);
		vo2.setFriend_idx(user_idx);
		res = friend_dao.delete(vo2);
		
		return "redirect:index.do";
	}
	
	
	

	
	
	
	
	
}
