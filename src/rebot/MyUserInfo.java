import com.jcraft.jsch.UserInfo;


public class MyUserInfo implements UserInfo{
	@Override
	public void showMessage(String paramString) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean promptYesNo(String paramString) {
		return true;
	}
	
	@Override
	public boolean promptPassword(String paramString) {
		return false;
	}
	
	@Override
	public boolean promptPassphrase(String paramString) {
		return false;
	}
	@Override
	public String getPassword() {
		return null;
	}
	
	@Override
	public String getPassphrase() {
		return null;
	}
}
