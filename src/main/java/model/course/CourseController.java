package model.course;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.course.*;

import general.BaseResponse;
import util.CookieHelper;
import util.Json;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Servlet implementation class course
 */
@WebServlet("/course/*")
public class CourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	CourseService service = null;
	CourseProvider provider = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseController() {
        super();
        try {
    		service = CourseService.getInstance();
    		provider = CourseProvider.getInstance();
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
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    Cookie idCookie = CookieHelper.getCookie(request, "id");
	    if(idCookie == null) {
	    	response.sendRedirect("main.jsp");
	    	return;
	    }
		String subPath = request.getPathInfo();
		System.out.println(subPath);
		if (subPath.equals("/all")) {
			BaseResponse<ArrayList<ArrayList<Course>>> result = provider.getAllCourses(idCookie.getValue());
			response.setContentType("application/json;");	
			response.getWriter().print(Json.convertMatrix(result));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 	enroll course
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setHeader("Access-Control-Allow-Origin", "*");
		String sid = (String)request.getParameter("sid");
		if (sid == null) {
			Cookie idCookie = CookieHelper.getCookie(request, "id");
			if (idCookie != null) {
				sid = idCookie.getValue();
			}
		}
		if (sid == null) {
			response.sendRedirect("main.jsp");
			return;
		}
		String subPath = request.getPathInfo();
		if ("/single".equals(subPath)) {
			String courseNumber = request.getParameter("courseNumber");
			String grade = request.getParameter("grade");
			int enrollSemester = Integer.parseInt(request.getParameter("enrollSemester"));
			int enrollYear = Integer.parseInt(request.getParameter("enrollYear"));
			int studentSemester = Integer.parseInt(request.getParameter("studentSemester"));
			System.out.println(request.getParameter("enrollSemester"));
			BaseResponse<ArrayList<Course>> result = service.enrollCourse(
				sid, courseNumber, grade, enrollSemester, enrollYear, studentSemester );
			System.out.println("do post course");
			response.sendRedirect("mycourse.html");
		}
		else if ("/all".equals(subPath)) {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			String token = "";
			JSONParser parser = new JSONParser();
			while (token != null) {
				token = br.readLine();
				if (token == null) break;
				json += token;
			}
			try {
				System.out.println(json);
				JSONObject jsonObj = (JSONObject)parser.parse(json);
				jsonObj = (JSONObject) jsonObj.get("courses");
				System.out.println(jsonObj);
				ArrayList<Course> insertCourses = Json.extractArr(jsonObj, "insert");
				ArrayList<Course> deleteCourses = Json.extractArr(jsonObj, "delete");
				ArrayList<Course> updateCourses = Json.extractArr(jsonObj, "update");
				BaseResponse<Integer> fetch = service.updateCourses(sid, insertCourses, updateCourses, deleteCourses);
				if (fetch.getIsSuccess()) {
					BaseResponse<ArrayList<ArrayList<Course>>> course = provider.getAllCourses(sid);
					response.getWriter().print(Json.convertMatrix(course));
				}else {
					response.getWriter().print(Json.convertInteger(fetch).toString());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else if (subPath.equals("/delete/single")) {
			doDelete(request,response);
		}
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		Cookie idCookie = CookieHelper.getCookie(request, "id");
		if (idCookie == null) {
			response.sendRedirect("main.jsp");
			return;
		}
		String sid = idCookie.getValue();
		try {
			JSONObject object = Json.parseJSON(request);
			BaseResponse<Course> result = service.unenrollCourse(sid,
				(String)object.get("id"),
				Integer.parseInt((String)object.get("studentSemester"))
			);
			BaseResponse<ArrayList<ArrayList<Course>>> ret = provider.getAllCourses(sid);
			response.getWriter().print(Json.convertMatrix(ret));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
