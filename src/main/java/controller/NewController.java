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

/*
 * 新規作成
 */

@WebServlet("/new")
public class NewController extends HttpServlet {
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException {
		
		// 作成した日付を作成（デフォルト：今日）
		// Calendarクラスのオブジェクトを生成する
		Calendar calendar = Calendar.getInstance();
		
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
		
		// メッセージ
		request.setAttribute("message", "新規ページです");
		
		// new.jspへリダイレクト
		String view = "/WEB-INF/views/new.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
}
