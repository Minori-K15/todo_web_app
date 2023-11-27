package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionController
 */
@WebServlet("/session")
public class SessionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// セッション接続確認
		session(request, response);
	}
	
	// セッション接続確認
	private void session(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// サーバーの保持するセッションを取得する
		HttpSession session = request.getSession(false);
		if (session == null) {
			// 未ログインの場合、ログイン画面に遷移
			response.sendRedirect("login");
		}	else {
			// セッションの破棄
			session.invalidate();
			
			try {
				request.setAttribute("message", "ログアウトしました" + "<br>"+ "ログインしてください");
				// login画面にリダイレクト
				// response.sendRedirect("login");
				
				// listへフォワード
				String view = "/WEB-INF/views/login.jsp";
				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				dispatcher.forward(request, response);
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
			catch (ServletException e) {
				e.printStackTrace();
			}
		}
	}
}

