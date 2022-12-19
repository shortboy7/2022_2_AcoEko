package model.student;

import model.course.Course;
import model.course.CourseUtil;
import model.dao.*;
import java.sql.*;
import java.util.ArrayList;
import model.student.*;
import model.student.Student;

public class StudentDao extends DAO{
	private static StudentDao dao;
	public static StudentDao getInstance() throws ClassNotFoundException {
		if (dao == null) {
			dao = new StudentDao();
			return dao;
		}
		return dao;
	}
	private StudentDao() throws ClassNotFoundException{
		super();
	}
	public Student selectStudentOnly(String sid) 
			throws SQLException, ClassNotFoundException
	{
		String sql = "SELECT * from student where sid = ?";
		Student student = new Student();
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			rs = ptmt.executeQuery();
			if (rs.next()) {
				student.setSid(rs.getString("sid"));
				student.setMajor(rs.getString("major"));
				student.setPw(rs.getString("pw"));
				student.setSemester(rs.getInt("semester"));
				student.setPhoneNumber(rs.getString("phoneNumber"));
				student.setStnum(rs.getString("stnum"));
				student.setName(rs.getString("name"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return student;
	}
	public Student selectStudent(String sid)
			throws SQLException, ClassNotFoundException
	{
		String sql = "select "
				+ "course.name, course.courseNumber, course.credit, isElective, mandatoryCode, "
				+ "grade, enroll.studentSemester, enroll.enrollYear, enroll.enrollSemester "
				+ "from student "
				+ "join enroll on student.sid = enroll.sid "
				+ "join course on enroll.courseNumber = course.courseNumber "
				+ "where student.sid = ?";
		Student student = selectStudentOnly(sid);
		int		mandatoryMajorCredit = 0;
		int		selectiveMajorCredit = 0;
		int		mandatoryElectCredit = 0;
		int		selectiveElectCredit = 0;
		
		int		totalScoreCredit = 0;
		int		totalMajorScoreCredit = 0;
		double	totalScore = 0;
		double	totalMajorScore = 0;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			rs = ptmt.executeQuery();
			while (rs.next()) {
				boolean elective = rs.getBoolean("isElective");
				String mCode = rs.getString("mandatoryCode");
				String grade = rs.getString("enroll.grade");
				double score = CourseUtil.getScore(grade);
				int	credit = rs.getInt("credit");
				// 평점 계산용 A+ ~ D
				if (!grade.equals("I") && !grade.equals("F") && !grade.equals("P") ) {
					totalScoreCredit += credit;
					totalScore += credit * score;
					if (!elective) {
						totalMajorScoreCredit += credit;
						totalMajorScore += credit * score;
					}
				}
				// 학점 계산용 A+ ~ D, P 
				if (!grade.equals("F") && !grade.equals("I")) {
					if (elective) {
						if (mCode.equals("M") || mCode.equals("C"))	mandatoryElectCredit += credit;
						else selectiveElectCredit += credit;
					}
					else {
						if (mCode.equals("M") || mCode.equals("C")) mandatoryMajorCredit += credit;
						else selectiveMajorCredit += credit;
					}
				}
			}
			student.setMandatoryMajorCredit(mandatoryMajorCredit);
			student.setMandatoryElectCredit(mandatoryElectCredit);
			student.setSelectiveMajorCredit(selectiveMajorCredit);
			student.setSelectiveElectCredit(selectiveElectCredit);
			if (Double.compare(totalMajorScoreCredit, (float) 0.0) != 0)
				student.setTotalMajorScore(totalMajorScore / totalMajorScoreCredit);
			if (Double.compare(totalScoreCredit, (float)0.0) != 0)
				student.setTotalScore(totalScore / totalScoreCredit);
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			close(conn, ptmt, rs);
		}
		return student;
	}


	public int insertStudent(String sid, String stnum, String pw, String name, int semester, String major, String phoneNumber)
			throws SQLException, ClassNotFoundException {
		int result = -1;
		String sql = "INSERT INTO student VALUES(?,?, ?, ?, ?, ?, ?)";
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			ptmt.setString(2, stnum);
			ptmt.setString(3, pw);
			ptmt.setString(4, name);
			ptmt.setInt(5, semester);
			ptmt.setString(6, major);
			ptmt.setString(7, phoneNumber);
			result = ptmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		finally {
			close(conn, ptmt, rs);
		}
		return result;
	}
};
