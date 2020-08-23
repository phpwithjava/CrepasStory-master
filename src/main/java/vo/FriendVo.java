package vo;

public class FriendVo {

	int f_idx;
	int user_idx;
	int friend_idx;
	int state;
	String regdate;
	
	UserVo friend_info;
	
	public UserVo getFriend_info() {
		return friend_info;
	}
	public void setFriend_info(UserVo friend_info) {
		this.friend_info = friend_info;
	}
	public int getF_idx() {
		return f_idx;
	}
	public void setF_idx(int f_idx) {
		this.f_idx = f_idx;
	}
	public int getUser_idx() {
		return user_idx;
	}
	public void setUser_idx(int user_idx) {
		this.user_idx = user_idx;
	}
	public int getFriend_idx() {
		return friend_idx;
	}
	public void setFriend_idx(int friend_idx) {
		this.friend_idx = friend_idx;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	
}
