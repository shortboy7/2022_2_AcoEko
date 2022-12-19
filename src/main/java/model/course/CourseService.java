package model.course;

import java.util.Iterator;
import java.util.ArrayList;

import general.BaseResponse;
import model.student.Student;
import model.student.StudentDao;
import model.course.*;

public class CourseService {
	private CourseDao dao = null;
	private static CourseService service = null;
	public static CourseService getInstance() throws Exception {
		if (service == null) {
			service = new CourseService();
		}
		return service;
	}
	private CourseService() throws Exception {
		dao = CourseDao.getInstance();
	}
	
	public BaseResponse<ArrayList<Course>> enrollCourse(String sid, String wantCourse, String grade,
			int enrollSemester, int enrollYear, int studentSemester) {
		try {
			ArrayList<Course> enrolledCourses = dao.selectAllEnrolledCourses(sid);
			Iterator<Course> iterator = enrolledCourses.iterator();
			while(iterator.hasNext()) {
				Course course = iterator.next();
				if (course.getCourseNumber().equals(wantCourse)) {
					return new BaseResponse<ArrayList<Course>>(false, "이미 등록한 강의입니다.", null);
				}
			}
			dao.enrollCourse(sid, wantCourse, grade, enrollSemester, enrollYear, studentSemester);
			return new BaseResponse<ArrayList<Course>>(true, "수강 등록 성공", null);			
		}catch(Exception e) {
			return new BaseResponse<ArrayList<Course>>(false, e.getMessage(), null);
		}
	}
	
	public BaseResponse<Course> unenrollCourse(String sid, String wantCourse, int studentSemester) {
		try {
			int result = dao.unenrollCourse(sid, wantCourse, studentSemester);
			return new BaseResponse<Course>(false, "수강 취소 성공", null);
		}catch(Exception e) {
			return new BaseResponse<Course>(false, e.getMessage(), null);
		}
	}
	
	public BaseResponse<Integer> updateCourses(String sid, ArrayList<Course> insertCourses, ArrayList<Course> updateCourses, ArrayList<Course> deleteCourses){
		try {
			int result = 0;
			result += 2 * dao.deleteCourses(sid, deleteCourses);
			result += 3 * dao.updateCourses(sid, updateCourses);
			result += 5 * dao.insertCourses(sid, insertCourses);
			return new BaseResponse<Integer>(true, "update 성공", result);
		}catch(Exception e) {
			return new BaseResponse<Integer>(false, e.getMessage(), 0);
		}
	}
}
