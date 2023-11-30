package model;

/**
 * Value チェック
 */
public class ValueCheck {
	
	public boolean checkUserName(String username) {
		int namelength = username.length();
		if (namelength == 0) {
			return false;
			// throw new RuntimeException("ユーザー名の値がありません");
		} else if (namelength >= 4 && namelength <= 16) {
			return true;
		} else {
		return false;
		}
	}
	
	public boolean checkPassword(String password) {
		int passwordlength = password.length();
		if (passwordlength == 0) {
			return false;
			// throw new RuntimeException("ユーザー名の値がありません");
		} else if (passwordlength >= 4 && passwordlength <= 16) {
			return true;
		} else {
		return false;
		}
	}
	
	public boolean checkHashPassword (String hashpassword) {
		System.out.println(hashpassword);
		int hashpasswordLength = hashpassword.length();
		if (hashpasswordLength == 0) {
			return false;
			// throw new RuntimeException("パスワードのハッシュ化がされていません");
		} else if (hashpasswordLength == 64) {
			return true;
		} else {
			return false;
		}
	}
}