package vo;

import org.springframework.web.multipart.MultipartFile;

public class PhotoVo {

	int photo_idx, user_idx, post_idx;
	String photoname, regdate;
	
	MultipartFile photo;
	
	public int getPost_idx() {
		return post_idx;
	}
	public void setPost_idx(int post_idx) {
		this.post_idx = post_idx;
	}
	public String getPhotoname() {
		return photoname;
	}
	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}
	public MultipartFile getPhoto() {
		return photo;
	}
	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}
	public int getPhoto_idx() {
		return photo_idx;
	}
	public void setPhoto_idx(int photo_idx) {
		this.photo_idx = photo_idx;
	}
	public int getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
	}
	public String getRegdate() {
		return regdate.substring(0,19);
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
}
