package com.ic.crepas_story;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.CommentDao;
import dao.FriendDao;
import dao.PhotoDao;
import dao.PostDao;
import dao.UserDao;
import vo.CommentVo;
import vo.FriendVo;
import vo.PhotoVo;
import vo.PostVo;
import vo.UserVo;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PostController {

	final static String VIEW_PATH = "/WEB-INF/views/";

	UserDao user_dao;
	PostDao post_dao;
	PhotoDao photo_dao;
	CommentDao comment_dao;
	FriendDao friend_dao;

	@Autowired
	HttpSession session;

	@Autowired
	ServletContext application;

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

	public CommentDao getComment_dao() {
		return comment_dao;
	}

	public void setComment_dao(CommentDao comment_dao) {
		this.comment_dao = comment_dao;
	}

	public FriendDao getFriend_dao() {
		return friend_dao;
	}

	public void setFriend_dao(FriendDao friend_dao) {
		this.friend_dao = friend_dao;
	}

	// ó�� �α��� �� �ҷ����� �Խñ� ���(��� ���� ����)
	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public String main(@RequestParam(value = "endPage", defaultValue = "5") int endPage,
			@RequestParam(value = "startPage", defaultValue = "1") int startPage, Model model) {

		Map pageMap = new HashMap();
		// �������� �������� �ҷ���
		UserVo user = (UserVo) session.getAttribute("user");

		// ��ũ�� ����¡ ����
		pageMap.put("user_idx", user.getUser_idx());
		pageMap.put("start", startPage);
		pageMap.put("end", endPage);

		// �α��� ���� �Խñ� �ҷ�����(����¡ ����)
		List<PostVo> list = post_dao.selectList(pageMap);
		for (PostVo vo : list) {
			// �Խñ� �� ��۸��
			vo.setComment(comment_dao.selectList(vo.getPost_idx()));
			// ��ۿ� ��� �� �������� �߰�
			for(CommentVo comment : vo.getComment()) {
				UserVo c_user = user_dao.selectOne(comment.getUser_idx());
				comment.setUser(c_user);
			}
			// �Խñ� �� ���� ����
			vo.setUser(user);
			// �Խñ� ����
			vo.setPhoto(photo_dao.selectList(vo.getPost_idx()));
			
			vo.setPhoto_all(photo_dao.All_List(user.getUser_idx()));
		}

		// ȭ�� ���� ģ����� ��ȸ
		List<FriendVo> friend = friend_dao.selectList(user.getUser_idx());
		for (FriendVo vo : friend) {
			UserVo friend_info = user_dao.selectOne(vo.getFriend_idx());
			vo.setFriend_info(friend_info);
		}

		model.addAttribute("list", list);
		model.addAttribute("friend", friend);

		return VIEW_PATH + "story.jsp";
	}

	@RequestMapping(value = "/list_json.do", method = RequestMethod.GET)
	@ResponseBody
	public List main_json(@RequestParam(value = "endPage", defaultValue = "5") int endPage,
			@RequestParam(value = "startPage", defaultValue = "1") int startPage) {

		Map pageMap = new HashMap();
		UserVo user = (UserVo) session.getAttribute("user");

		pageMap.put("user_idx", user.getUser_idx());
		pageMap.put("start", startPage);
		pageMap.put("end", endPage);

		List<PostVo> list = post_dao.selectList(pageMap);
		for (PostVo vo : list) {
			vo.setComment(comment_dao.selectList(vo.getPost_idx()));
			for(CommentVo comment : vo.getComment()) {
				UserVo c_user = user_dao.selectOne(comment.getUser_idx());
				comment.setUser(c_user);
			}
			vo.setUser(user);
			vo.setPhoto(photo_dao.selectList(vo.getPost_idx()));
			
			vo.setPhoto_all(photo_dao.All_List(user.getUser_idx()));
		}

		return list;
	}

	// ģ���� �Խñ� �ҷ�����
	@RequestMapping(value = "/board.do", method = RequestMethod.GET)
	@ResponseBody
	public Map board(@RequestParam(value = "endPage", defaultValue = "5") int endPage,
			@RequestParam(value = "startPage", defaultValue = "1") int startPage, int user_idx) {

		Map map = new HashMap();
		Map pageMap = new HashMap();

		UserVo user = user_dao.selectOne(user_idx);

		pageMap.put("user_idx", user_idx);
		pageMap.put("start", startPage);
		pageMap.put("end", endPage);

		List<PostVo> list = post_dao.selectList(pageMap);
		for (PostVo vo : list) {
			vo.setComment(comment_dao.selectList(vo.getPost_idx()));
			for(CommentVo comment : vo.getComment()) {
				UserVo c_user = user_dao.selectOne(comment.getUser_idx());
				comment.setUser(c_user);
			}
			vo.setUser(user);
			vo.setPhoto(photo_dao.selectList(vo.getPost_idx()));
		}

		map.put("list", list);
		map.put("user", user);

		return map;
	}

	// ���� �̹��� ���ε� �߰� �ڵ�
	@RequestMapping(value = "/post_insert.do", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Map insert(String content, PhotoVo photovo) throws Exception {

		Map map = new HashMap();
		UserVo user = (UserVo) session.getAttribute("user");

		PostVo vo = new PostVo();
		vo.setContent(content);
		vo.setUser_idx(user.getUser_idx());
		photovo.setUser_idx(user.getUser_idx());

		String filename = "no_file";
		// ���ε�� ���� ������ �����ϴ� ��ü(MultipartFile)
		if (!photovo.getPhoto().isEmpty()) {
			// ���ε�� ������ �����ϸ�
			filename = photovo.getPhoto().getOriginalFilename();
			// �ӽ���ġ�� ���ε�� ������ ���� ������ ��ġ�� �����ؾ� �ȴ�
			String web_path = "/resources/images/";
			// ������ ���ϱ� (Main Controller���� ��û)
			String abs_path = application.getRealPath(web_path);

			// ������ ���� ����
			File save = new File(abs_path, filename);

			// �̹� ������ �����ϳ�? (if��) (1/1000�� ������ �Ȱ��� ������ �� �ö�ü��� ������ �����ϰ� while)
			while (save.exists()) {
				// ���� �ð��� 1/1000 �� ������ ���ϱ�
				long time = System.currentTimeMillis();

				int index = filename.lastIndexOf(".");
				String f_name = filename.substring(0, index);
				String f_ext = filename.substring(index);

				filename = String.format("%s_%d%s", f_name, time, f_ext);

				save = new File(abs_path, filename);
			}

			// �ӽ� ���� => �������Ϸ� ����
			photovo.getPhoto().transferTo(save); // IOException
		}

		photovo.setPhotoname(filename);

		String result = "no";
		// Post Insert
		int res1 = post_dao.Insert(vo);

		int idx_res = post_dao.selectOne_post(user.getUser_idx());

		// Post_idx ������ Photo�� ���
		photovo.setPost_idx(idx_res);

		// Photo Insert
		int res2 = photo_dao.Insert(photovo);

		// Post, Photo 2���� ��� Insert �Ϸ�Ǹ� yes
		if (res1 == 1 && res2 == 1)
			result = "yes";
		else
			result = "no";

		map.put("result", result);
		return map;
	}

	@RequestMapping(value = "/post_update.do", method = RequestMethod.GET)
	@ResponseBody
	public Map update(PostVo vo) {

		Map map = new HashMap();

		String result = "no";
		int res = post_dao.update(vo);
		if (res == 1)
			result = "yes";
		map.put("result", result);

		return map;
	}

	@RequestMapping(value="/post_delete.do", method=RequestMethod.GET)
	@ResponseBody
	public Map delete(int post_idx){
		
		Map map = new HashMap();
		PhotoVo vo = photo_dao.selectOne(post_idx);
		
		String result = "no";
		
		int res1 = photo_dao.delete(post_idx);
		int res2 = post_dao.delete(post_idx);
		
		String web_path = "/resources/images/";
		String abs_path = application.getRealPath(web_path);
		
		File del = new File(abs_path, vo.getPhotoname());
		del.delete();
		
		if(res1==1 && res2==1)
			result = "yes";
		else
			result = "no";
		
		map.put("result", result);
		
		return map;
	}
}
