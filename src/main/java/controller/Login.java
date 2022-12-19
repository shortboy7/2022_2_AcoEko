package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import general.*;
import model.student.Student;
import model.student.StudentService;
import util.Json;
/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();

        // TODO Auto-generated constructor stub
    }

	/**
	 * @throws IOException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		StudentService service = StudentService.getInstance();
		BaseResponse<Student> result = service.logIn(request.getParameter("id"), request.getParameter("pwd"));
		if (result.getIsSuccess())
		{
			Cookie idCookie = new Cookie("id", result.getResult().getSid());
			idCookie.setPath("/");
			idCookie.setMaxAge(12*60*60);
			Cookie semCookie = new Cookie("semester", Integer.toString(result.getResult().getSemester()));
			semCookie.setPath("/");
			semCookie.setMaxAge(12 * 60 * 60);
			response.addCookie(idCookie);
			response.addCookie(semCookie);
			response.sendRedirect("MainPage");
		}else {
			System.out.print(result);
			System.out.println(result.getMessage());
			response.sendRedirect("main.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
