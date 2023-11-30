package juit;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ValueCheck;

/**
 * ValueCheck機能テスト
 */
public class ValueCheckTest {
	
	// INPUT TRUE
	private String userNameNullCheck = "yamada";
	private String userNumCheck = "yamadasan ";
	private String passNullCheck = "test";
	private String passNumCheck = "test";
	private String hashPassword = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
	
	// INPUT FALSE
	private String false_userNameNullCheck = "";
	private String false_userNumCheck = "yamada_hanako_desu";
	private String false_passNullCheck = "";
	private String false_passNumCheck = "yamada_hanako_desu";
	private String false_hashPassword = "9f86d081884c7d659a2feaa0c55ad015a3bf";
	
	// インスタンス生成
	ValueCheck ValueCheck = new ValueCheck();

	// 変数定義
	boolean username;
	boolean password;
	boolean hashpassword;
	
	/**
	 * ユーザー名のNullチェック
	 */
	@Test
	public void valueUserNameNullCheck() {
		// true
		username = ValueCheck.checkUserName(userNameNullCheck);
		assertEquals(true, username);
		// false
		username = ValueCheck.checkUserName(false_userNameNullCheck);
		assertEquals(false, username);
	}
	
	/**
	 * ユーザー名の文字数
	 */
	@Test
	public void valueUserNumCheck() {
		// true
		username = ValueCheck.checkUserName(userNumCheck);
		assertEquals(true, username);
		// false
		username = ValueCheck.checkUserName(false_userNumCheck);
		assertEquals(false, username);
	}
	
	/**
	 * パスワードのNullチェック
	 */
	@Test
	public void valuePassNullCheck() {
		// true
		password = ValueCheck.checkPassword(passNullCheck);
		assertEquals(true, password);
		// false
		password = ValueCheck.checkPassword(false_passNullCheck);
		assertEquals(false, password);
	}
	
	/**
	 * パスワードの桁数チェック
	 */
	@Test
	public void valuePassNumCheck() {
		// true
		password = ValueCheck.checkPassword(passNumCheck);
		assertEquals(true, password);
		// false
		password = ValueCheck.checkPassword(false_passNumCheck);
		assertEquals(false, password);
	}

	/**
	 * Hash化チェック
	 */
	@Test
	public void valueHashCheck() {
		// true
		hashpassword = ValueCheck.checkHashPassword(hashPassword);
		assertEquals(true, hashpassword);
		// false
		hashpassword = ValueCheck.checkHashPassword(false_hashPassword);
		assertEquals(false, hashpassword);
	}
}
