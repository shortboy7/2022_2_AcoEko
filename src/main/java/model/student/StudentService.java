package model.student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import general.BaseResponse;
import model.course.Course;

public class StudentService {
	static StudentDao dao = null;
	private static StudentService service = null;
	public static StudentService getInstance() {
		if (service == null) {
			service = new StudentService();
		}
		return service;
	}
	private StudentService() {
		try {
			dao = StudentDao.getInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public BaseResponse<Student> signUp(String sid, String stnum, String pw, String name, int semester,String major, String phoneNumber) {
		int result = -1;
		try {
			result = dao.insertStudent(sid,stnum, pw, name, semester, major, phoneNumber);
			return new BaseResponse<Student>(true, "회원가입에 성공하셨습니다", 
					new Student(sid,stnum, pw, name, semester, major, phoneNumber));
		}catch(Exception e) {
			e.printStackTrace();
			return new BaseResponse<Student>(false, e.getMessage(), null);
		}
	}
	public BaseResponse<Student> getStudent(String sid){
		try {
			Student student = dao.selectStudent(sid);
			return new BaseResponse<Student>(true,"회원 정보 조회 성공", student);
		}catch (Exception e) {
			e.printStackTrace();
			return new BaseResponse<Student>(false, e.getMessage(), null);
		}
	}
	public BaseResponse<Student> logIn(String sid, String pw) {
		try {
			Student student = dao.selectStudent(sid);
			if (student == null) {
				return new BaseResponse<Student>(false, "ID 오류", null);
			}
			System.out.println("login Controller : " + student.getSid());
			if (student.getPw().equals(pw))
				return new BaseResponse<Student>(true, "로그인 성공!", student);
			else
				return new BaseResponse<Student>(false, "비밀번호 오류", null);
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("cause sql exception login");
			return new BaseResponse<Student>(false, e.getLocalizedMessage(), null);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("cause normal exception login");
			return new BaseResponse<Student>(false, e.getMessage(), null);
		}
	}
	
}
