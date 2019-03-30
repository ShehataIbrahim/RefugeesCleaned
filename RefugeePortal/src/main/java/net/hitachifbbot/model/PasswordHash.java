package net.hitachifbbot.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * ハッシュ化したパスワードを保持する<br>
 * ハッシュ生成機能、パスワードが一致しているかの判定メソッドを含む
 */
public class PasswordHash {
	/**
	 * パスワードハッシュ
	 */
	public final String hash;
	/**
	 * ハッシュ化世代種別(1固定)
	 */
	public final int hashType;
	/**
	 * ソルト
	 */
	public final String salt;

	/**
	 * ハッシュ化済みの情報からインスタンスを生成するコンストラクタ
	 * @param hash ハッシュ
	 * @param salt ソルト
	 */
	public PasswordHash(String hash, String salt) {
		this.hash = hash;
		this.hashType = 1;
		this.salt = salt;
	}

	/**
	 * 平文からハッシュ化したパスワードを生成するコンストラクタ
	 * @param plaintext 平文のパスワード
	 * @throws InvalidKeySpecException ハッシュ化失敗時にスローされる
	 * @throws NoSuchAlgorithmException ハッシュ化失敗時にスローされる
	 * @throws DecoderException ハッシュ化失敗時にスローされる
	 */
	public PasswordHash(String plaintext) throws InvalidKeySpecException, NoSuchAlgorithmException, DecoderException{
		byte[] salt = new byte[64];
		new SecureRandom().nextBytes(salt);

		this.hashType = 1;
		this.salt = Hex.encodeHexString(salt);
		this.hash = calcHashV1(plaintext);
	}

	/**
	 * パスワードが一致しているか判定する
	 * @param target 一致しているか判定する平文
	 * @return 一致していればtrue/そうでなければfalse
	 * @throws InvalidKeySpecException ハッシュ化失敗時にスローされる
	 * @throws NoSuchAlgorithmException ハッシュ化失敗時にスローされる
	 * @throws DecoderException ハッシュ化失敗時にスローされる
	 */
	public boolean isMatch(String target) throws InvalidKeySpecException, NoSuchAlgorithmException, DecoderException{
		switch(hashType){
		case 1:
			return this.hash.equals(calcHashV1(target));
		}
		return false;
	}

	/**
	 * 第1世代のハッシュ化関数<br>
	 * ソルトはメンバ変数を参照する
	 * @param plaintext 平文
	 * @return 計算後のハッシュ
	 * @throws InvalidKeySpecException ハッシュ化失敗時にスローされる
	 * @throws NoSuchAlgorithmException ハッシュ化失敗時にスローされる
	 * @throws DecoderException ハッシュ化失敗時にスローされる
	 */
	public String calcHashV1(String plaintext) throws InvalidKeySpecException, NoSuchAlgorithmException, DecoderException{
		byte[] salt = Hex.decodeHex(this.salt.toCharArray());

		// SHA512 ストレッチング10000回でハッシュ化
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        KeySpec ks = new PBEKeySpec(plaintext.toCharArray(), salt, 10000, 512);
        SecretKey s = f.generateSecret(ks);
        return Hex.encodeHexString(s.getEncoded());
	}
	private static final String generateSalt()
	{
		 SecureRandom random = new SecureRandom();
	        byte bytes[] = new byte[64];
	        random.nextBytes(bytes);	
	        return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
	}
	public static void main(String[] args) {
			try {
				PasswordHash hash=new PasswordHash("password");
				System.out.println(hash.salt);
				System.out.println(hash.hash);
				
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DecoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
