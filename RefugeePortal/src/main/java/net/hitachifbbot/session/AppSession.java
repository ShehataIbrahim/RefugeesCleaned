package net.hitachifbbot.session;

import net.hitachifbbot.DB;
import net.hitachifbbot.model.DoctorUserData;
import net.hitachifbbot.model.NamminUserData;

import java.util.HashSet;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class AppSession {
	public static final String SESSION_KEY = "APP_SESSION";

	/**
	 * ユーザデータ
	 * ログインしていなければnull
	 */
	public UserData userData = null;
	/**
	 * CSRF対策トークン
	 */
	public HashSet<String> postToken = new HashSet<>();

	private static AppSession getSession(HttpServletRequest req){
		Object session = req.getSession().getAttribute(SESSION_KEY);
		if(session == null || !(session instanceof AppSession)){
			session = new AppSession();
		}
		return (AppSession)session;
	}


	private void updateSession(HttpServletRequest req){
		req.getSession().setAttribute(SESSION_KEY, this);
	}

	/**
	 * ユーザーデータを設定します。
	 * @param req HttpServletRequest
	 * @param userData 設定するユーザーデータ,nullならログアウト
	 */
	public static void setUserData(HttpServletRequest req,UserData userData){
		AppSession session = getSession(req);
		session.userData = userData;
		session.updateSession(req);
	}

	/**
	 * ユーザーデータを取得します
	 * @param req HttpServletRequest
	 * @return ユーザーデータ(ログインしていなければnull)
	 */
	public static UserData getUserData(HttpServletRequest req){
		return getSession(req).userData;
	}

	/**
	 * 難民ユーザーデータを取得します
	 * @param req HttpServletRequest
	 * @return 難民ユーザーデータ(難民ログインしていなければnull)
	 */
	public static NamminUserData getNamminUserData(HttpServletRequest req){
		UserData userData = getUserData(req);
		if(userData instanceof NamminUserData){
			return (NamminUserData)userData;
		}
		return null;
	}

	/**
	 * 医療従事者ユーザーデータを取得します
	 * @param req HttpServletRequest
	 * @return 医療従事者ユーザーデータ(医療従事者ログインしていなければnull)
	 */
	public static DoctorUserData getDoctorUserData(HttpServletRequest req){
		UserData userData = getUserData(req);
		if(userData instanceof NamminUserData){
			return (DoctorUserData)userData;
		}
		return null;
	}

	/**
	 * CSRFトークンを発行します
	 * @param req HttpServletRequest
	 * @return 生成したトークン
	 */
	public static String createToken(HttpServletRequest req){
		AppSession session = getSession(req);
		String token = UUID.randomUUID().toString().replace("-", "");
		session.postToken.add(token);
		session.updateSession(req);
		return token;
	}

	/**
	 * CSRFトークンを使用します
	 * @param req HttpServletRequest
	 * @param token 使用するトークン
	 * @return 正常にトークンを使用できたらtrue, そうでない場合はfalse
	 */
	public static boolean useToken(HttpServletRequest req, String token){
		AppSession session = getSession(req);
		boolean ret = session.postToken.remove(token);
		session.updateSession(req);
		return ret;
	}

	public abstract static class UserData{
	}

	/**
	 * 難民ユーザー
	 */
	
	/**
	 * 医師ユーザー
	 */
	
}
