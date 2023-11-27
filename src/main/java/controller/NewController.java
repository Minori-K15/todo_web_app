package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/*
 * 新規作成
 */

@WebServlet("/new")
public class NewController extends HttpServlet {
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		// メッセージ
		request.setAttribute("message", "新規ページです");
		
		// セッション接続
		String username = session(request, response);
		request.setAttribute("username", username);
		
		// 日付のフォーマット化
		calendar(Calendar.getInstance(), request);
		
		// new.jspへリダイレクト
		String view = "/WEB-INF/views/new.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
	
	// セッション接続
	private String session(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 変数の初期化
		String username = null;
		
		// サーバーの保持するセッションを取得する
		HttpSession session = request.getSession(false);
		if (session == null) {
			// 未ログインの場合、ログイン画面に遷移
			response.sendRedirect("login");
		} else {
			// セッションに保持されているユーザー名を取得
			session.getAttribute("id");
			username = (String) session.getAttribute("username");
			
			request.setAttribute("username", username);
			
			// メッセージの表示
			if (request.getAttribute("message") == null) {
				// nullの場合のメッセージ
				request.setAttribute("message", "todoを管理しましょう");
			}
		}
		return username;
	}
	
	// 日付
	protected void calendar(Calendar calendar, HttpServletRequest request) {
		
		//SimpleDateFormatクラスでフォーマットパターンを設定する
		SimpleDateFormat date_form = new SimpleDateFormat("yyyy/MM/dd");
		String days = date_form.format(calendar.getTime());
		
		// 作成日
		request.setAttribute("days", days);
		
		// 期限日数
		int limit_days = +5;
		
		// 期限日
		calendar.add(Calendar.DATE, limit_days);
		String limit = date_form.format(calendar.getTime());
		request.setAttribute("limit", limit);
	}
}
