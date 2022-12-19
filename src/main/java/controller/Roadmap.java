package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.RoadMap;
import general.BaseResponse;
import model.course.CourseProvider;
import model.course.CourseService;
import util.CookieHelper;

/**
 * Servlet implementation class Roadmap
 */
@WebServlet("/Roadmap")
public class Roadmap extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CourseService service = null;
	CourseProvider provider = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Roadmap() {
        super();
        try {
        	service = CourseService.getInstance();
        	provider = CourseProvider.getInstance();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie idCookie = CookieHelper.getCookie(request, "id");
		if (idCookie == null) {
			response.sendRedirect("main.jsp");
			return;
		}
		BaseResponse<RoadMap> result = provider.getRoadMapData(idCookie.getValue());
		request.setAttribute("res", result);
		RequestDispatcher dispatcher = request.getRequestDispatcher("loadmap.jsp");
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
