package model.student;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.student.*;

/**
 * Servlet implementation class StudentController
 */
@WebServlet("/Student")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentService service = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentController() {
        super();
        service = StudentService.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Cookie [] cookies = request.getCookies();
		for (int i = 0 ; i < cookies.length; i++) {
			if (cookies[i].getName().equals("id")) {
				request.setAttribute("student", service.getStudent(cookies[i].getValue()));
				break;
			}
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
