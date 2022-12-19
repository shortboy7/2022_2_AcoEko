package model.course;

import java.util.ArrayList;

import model.course.*;
import general.BaseResponse;
import util.Json;
import util.MathHelper;
import dto.MainStudent;
import dto.RoadMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class CourseProvider {
	CourseDao dao = null;
	private static CourseProvider provider = null;
	public static CourseProvider getInstance(){
		if (provider == null) {
			provider = new CourseProvider();
		}
 		return provider;
	}
	
	private CourseProvider() {
		try {
			dao = CourseDao.getInstance();			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public BaseResponse<ArrayList<Course>> getEnrolledCourse(String sid) {
		try {
			ArrayList<Course> result = null;
			result = dao.selectAllEnrolledCourses(sid);
			System.out.println(result.size());
			return new BaseResponse<ArrayList<Course>>(true, "모든 등록된 과목 조회 성공", result);
		}catch(Exception e) {
			return new BaseResponse<ArrayList<Course>>(false, e.getMessage(), null);
		}
	}

	public BaseResponse<ArrayList<Course>> getUnEnrolledCourse(String sid, int enrollSemester){
		try {
			ArrayList<Course> result = null;
			result = dao.selectAllUnenrolledCourses(sid, enrollSemester);
			return new BaseResponse<ArrayList<Course>>(false, "수강하지 않았던 과목들", result);
		}catch(Exception e) {
			return new BaseResponse<ArrayList<Course>>(false, e.getMessage(), null);
		}
	}

	
	public BaseResponse<ArrayList<ArrayList<Course>>> getRecommendCourse(String sid, int maxCredit, int curSemester) {
		try {
			String suc = dao.getSuccessTagString(sid);
			ArrayList<Course> recommendation = dao.selectRecommendMandatoryCourse(sid, curSemester, suc);
			Iterator<Course> iter = recommendation.iterator();
			int curCredit = 0;
			while(iter.hasNext()) {
				curCredit += iter.next().getCredit();
			}
			if (curCredit < maxCredit) {
				ArrayList<Course> addictive = dao.selectRecommendSelectiveCourse(sid, curSemester, suc);
				recommendation.addAll(addictive);
			}
			ArrayList<ArrayList<Course>> result = MathHelper.makeCombination(recommendation, maxCredit);
			return new BaseResponse<ArrayList<ArrayList<Course>>>(true, "추천 성공", result);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("추천 강좌들");
			return new BaseResponse<ArrayList<ArrayList<Course>>>(false, e.getMessage(), null);
		}
	}
	
	public BaseResponse<RoadMap> getRoadMapData(String sid){
		try {
			RoadMap data = new RoadMap();
			ArrayList<Course> enrolled = dao.selectAllEnrolledCourses(sid);			
			ArrayList<Course> gradedCourses = new ArrayList<Course>();
			ArrayList<Course> enrollingCourses = new ArrayList<Course>();
			data.setOpenCourses(dao.getOpenCourses(sid));
			data.setCloseCourse(dao.getCloseCourses(sid));
			Iterator<Course> iter = enrolled.iterator();
			while (iter.hasNext()) {
				Course course = iter.next();
				if (course.getGrade().equals("I")) enrollingCourses.add(course);
				else gradedCourses.add(course);
			}
			data.setGradedCourses(gradedCourses);
			data.setEnrollingCourses(enrollingCourses);
			return new BaseResponse<RoadMap>(true, "성공", data);
		}catch(Exception e) {
			return new BaseResponse<RoadMap>(false, e.getMessage(), null);
		}
	}
	public BaseResponse<ArrayList<ArrayList<Course>>> getAllCourses(String sid) {
		try {
			ArrayList<ArrayList<Course>> result = new ArrayList<ArrayList<Course>>();
			ArrayList<Course> enrolled = dao.selectAllEnrolledCourses(sid);			
			ArrayList<Course> gradedCourses = new ArrayList<Course>();
			ArrayList<Course> enrollingCourses = new ArrayList<Course>();
			Iterator<Course> iter = enrolled.iterator();
			while (iter.hasNext()) {
				Course course = iter.next();
				if (course.getGrade().equals("I")) enrollingCourses.add(course);
				else gradedCourses.add(course);
			}
			ArrayList<Course> openCourse = dao.getOpenCourses(sid);
			ArrayList<Course> closeCourse = dao.getCloseCourses(sid);
			result.add(gradedCourses);
			result.add(enrollingCourses);
			result.add(openCourse);
			result.add(closeCourse);
			return new BaseResponse<ArrayList<ArrayList<Course>>>(true, "성공", result);
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<ArrayList<ArrayList<Course>>>(false, e.getMessage(), null);
		}
	}
	public BaseResponse<ArrayList<Course>> getEnrolledCourses(String sid, int enrolledSemester, int enrolledYear){
		ArrayList<Course> result = null;
		ArrayList<Course> want = new ArrayList<Course>();
		try {
			result = dao.selectAllCourses(sid);
			Iterator<Course> iter = result.iterator();
			System.out.println(enrolledSemester);
			System.out.println(enrolledYear);
			while(iter.hasNext()) {
				Course course = iter.next();
				System.out.println("학기 " + course.getEnrollSemester());
				System.out.println("년도 " + course.getEnrollYear());
				if(course.getEnrollSemester() == enrolledSemester && course.getEnrollYear() == enrolledYear)
					want.add(course);
			}
			System.out.println(want.size());

			return new BaseResponse<ArrayList<Course>>(true, "성공", want);
		}catch(Exception e) {
			return new BaseResponse<ArrayList<Course>>(false, e.getMessage(), null);
		}
	}
}
