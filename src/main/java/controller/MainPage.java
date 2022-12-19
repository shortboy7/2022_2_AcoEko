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

import dto.MainStudent;
import general.BaseResponse;
import model.course.CourseProvider;
import model.course.CourseService;
import model.student.Student;
import model.student.StudentService;
import util.CookieHelper;

/**
 * Servlet implementation class MainPage
 */
@WebServlet("/MainPage")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    CourseProvider courseProvider = null;
    StudentService studentService = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainPage() {
        super();
        try {
    		if (studentService == null) studentService = StudentService.getInstance(); 
    		if (courseProvider == null) courseProvider = CourseProvider.getInstance();
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			Cookie idCookie = CookieHelper.getCookie(request, "id");
			if (idCookie == null) {
				response.sendRedirect("main.jsp");
				return;
			}
			BaseResponse<Student> res = studentService.getStudent(idCookie.getValue());
			request.setAttribute("res", res);
			RequestDispatcher dispatcher = request.getRequestDispatcher("mypage.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
