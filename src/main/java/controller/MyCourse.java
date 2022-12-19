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

import model.course.Course;
import model.course.CourseProvider;
import model.student.Student;
import model.student.StudentService;
import util.CookieHelper;
import general.BaseResponse;
/**
 * Servlet implementation class MyCourse
 */
@WebServlet("/MyCourse")
public class MyCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentService studentService = null;
    private CourseProvider courseProvider = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyCourse() {
        super();
        studentService = StudentService.getInstance();
        courseProvider = CourseProvider.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie IDcookie = CookieHelper.getCookie(request, "id");
		if (IDcookie == null) {
			response.sendRedirect("main.jsp");
			return;
		}
		BaseResponse<Student> student = studentService.getStudent(IDcookie.getValue());
//		BaseResponse<ArrayList<ArrayList<Course>>> courses = 
		RequestDispatcher dispatcher = request.getRequestDispatcher("mycourse.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
