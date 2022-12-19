package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import general.BaseResponse;
import model.course.Course;
import model.course.CourseProvider;
import model.course.CourseService;
import util.CookieHelper;
import util.Json;
import util.MathHelper;

/**
 * Servlet implementation class RecommendPage
 */
@WebServlet("/RecommendPage")
public class RecommendPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CourseProvider provider = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendPage() {
    	 super();
         try {
     		if (provider == null) provider = CourseProvider.getInstance();
     	}catch(Exception e) {
     		System.out.println(e.getMessage());
     		System.out.println("load fail");
     	}
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		Cookie idCookie = CookieHelper.getCookie(request, "id");
		if (idCookie == null) {
			response.sendRedirect("main.jsp");
			return;
		}
		System.out.println(idCookie.getValue());
		BaseResponse<ArrayList<ArrayList<Course>>> result = provider.getRecommendCourse(
			idCookie.getValue(),
			Integer.parseInt(request.getParameter("maxCredit")),
			Integer.parseInt(request.getParameter("semester"))
		);
		request.setAttribute("result", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("recommandResult.jsp");
		dispatcher.forward(request, response);
		//response.getWriter().print(Json.convertMatrix(result));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
