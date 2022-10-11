
public class userCheck {
	User loginUser;
	boolean isLogin;
	public userCheck(User user, boolean isLogin) {
		
	}
	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	public boolean isLogin() {
		return isLogin;
	}
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
	@Override
	public String toString() {
		return "userCheck [loginUser=" + loginUser + ", isLogin=" + isLogin + "]";
	}
	
}


//						String loginUserId = uDI.login_read(id_tf.getText(), pw_tf.getText()).getUser_id();
//						String loginUserName = uDI.login_read(id_tf.getText(), pw_tf.getText()).getUser_Name();
//						String loginUserPassword = uDI.login_read(id_tf.getText(), pw_tf.getText()).getUser_password();
//						User loginUser = new User(loginUserId, loginUserName, loginUserPassword);
//	userCheck uC = new userCheck(loginUser, true);