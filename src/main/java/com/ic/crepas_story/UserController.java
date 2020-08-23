package com.ic.crepas_story;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.UserDao;
import vo.UserVo;

@Controller
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserController {

	final static String VIEW_PATH = "/WEB-INF/views/";

	Map map;

	@Autowired
	ServletContext application;

	@Autowired
	HttpSession session;

	UserDao user_dao;

	public UserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(UserDao user_dao) {
		this.user_dao = user_dao;
	}

	@RequestMapping("/index.do")
	public String home() {
		
		return VIEW_PATH + "index.jsp";
	}

	// ID ,PWD Login Check
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public Map login(String userid, String password) {

		map = new HashMap();

		UserVo vo = user_dao.selectOne(userid);

		String result = "yes";

		if (vo == null || !vo.getPassword().equals(password)) {
			result = "no";
		} else {
			session.setAttribute("user", vo);
		}

		map.put("result", result);

		return map;
	}

	@RequestMapping("/logout.do")
	public String logout() {
		// String result = "yes";
		session.removeAttribute("user");

		return "redirect:index.do";
	}

	@RequestMapping(value = "/member_insert.do", method = RequestMethod.POST)
	@ResponseBody
	public Map member_insert(UserVo vo) throws Exception {

		map = new HashMap();

		String filename = "profile.jpg";

		if (!vo.getPhoto().isEmpty()) {
			filename = vo.getPhoto().getOriginalFilename();
			String web_path = "/resources/images/";
			String abs_path = application.getRealPath(web_path);

			//System.out.println(abs_path);

			File save = new File(abs_path, filename);

			while (save.exists()) {
				long time = System.currentTimeMillis();

				int index = filename.lastIndexOf(".");
				String f_name = filename.substring(0, index);
				String f_ext = filename.substring(index);

				filename = String.format("%s_%d%s", f_name, time, f_ext);
				save = new File(abs_path, filename);
			}
			vo.getPhoto().transferTo(save);
		}

		// vo.setFilename(filename);
		vo.setProfile(filename);

		String result = "no";
		int res = user_dao.insert(vo);
		// System.out.println("ȸ������ ����");
		if (res == 1)
			result = "yes";

		map.put("result", result);

		return map;
	}

	@RequestMapping(value = "/check_id.do", method = RequestMethod.POST)
	@ResponseBody
	public Map check_id(String userid) {

		map = new HashMap();

		UserVo vo = user_dao.selectOne(userid);
		// System.out.println(id);
		// ID �������
		String result = "no";

		if (vo == null) // DB���� ������ id�� �������� �ʴ´ٴ� ��
			result = "yes"; // ��밡��
		// JSON ����
		map.put("result", result);

		return map;
	}

	// ȸ�� ���� ���� (JSON)
	@RequestMapping("/project/info_form.do")
	@ResponseBody
	public UserVo info_form(int user_idx) {

		UserVo vo = user_dao.selectOne(user_idx);
		// model.addAttribute("vo", vo);

		return vo;
	}

	// ȸ�� ���� ���� �� ����
	@RequestMapping("/project/user_modify_form.do")
	public String user_modify_form(int user_idx, Model model) {

		UserVo vo = user_dao.selectOne(user_idx);
		// System.out.println(vo.getProfile());
		model.addAttribute("vo", vo);

		return VIEW_PATH + "user_modify_form.jsp";
	}

	// ȸ�� ���� ���� (JSON)
	   @RequestMapping("/user_modify.do")
	   @ResponseBody
	   public Map user_modify(UserVo vo) throws Exception {

	      map = new HashMap();

	      String filename = "profile.jpg";

	      if (!vo.getPhoto().isEmpty()) { // �����פ�����
	         filename = vo.getPhoto().getOriginalFilename();
	         String web_path = "/resources/images/";
	         String abs_path = application.getRealPath(web_path);

	         System.out.println(abs_path);

	         File save = new File(abs_path, filename);

	         while (save.exists()) {
	            long time = System.currentTimeMillis();

	            int index = filename.lastIndexOf(".");
	            String f_name = filename.substring(0, index);
	            String f_ext = filename.substring(index);

	            filename = String.format("%s_%d%s", f_name, time, f_ext);
	            save = new File(abs_path, filename);
	         }

	         vo.getPhoto().transferTo(save);
	         vo.setProfile(filename);

	      }

	      int res = user_dao.update(vo);
	      // System.out.println(vo.getProfile());

	      // res ����� ���� result �� ó��
	      String result = "no";

	      if (res == 1)
	         result = "yes";
	      map.put("result", result);

	      return map;
	   }
}
