package model.course;

import model.dao.*;
import model.student.Student;
import model.course.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class CourseDao extends DAO{
	private static CourseDao dao = null;
	public static CourseDao getInstance() throws ClassNotFoundException {
		if (dao == null) {
			dao = new CourseDao();
		}
		return dao;
	}
	private CourseDao() throws ClassNotFoundException{
		super();
	}

	public ArrayList<Course> selectAllEnrolledCourses(String sid)
			throws SQLException, ClassNotFoundException
	{
		String sql ="SELECT * FROM course "
		+ "JOIN enroll ON course.courseNumber = enroll.courseNumber "
		+ "JOIN student ON enroll.sid = student.sid "
		+ "WHERE student.sid = ?";
		ArrayList<Course> result = null;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			rs = ptmt.executeQuery();
			result = new ArrayList<Course>();
			while (rs.next()) {
				Course course = new Course();
				course.setCourseNumber(rs.getString("course.courseNumber"));
				course.setCredit(rs.getInt("credit"));
				course.setGrade(rs.getString("grade"));
				course.setType(rs.getString("types"));
				course.setName(rs.getString("course.name"));
				course.setRecommendSemester(rs.getInt("recommendSemester"));
				course.setEnrollSemester(rs.getInt("enrollSemester"));
				course.setEnrollYear(rs.getInt("enrollYear"));
				course.setIsElective(rs.getBoolean("isElective"));
				course.setMandatoryCode(rs.getString("mandatoryCode"));
				course.setStudentSemester(rs.getInt("studentSemester"));
				result.add(course);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}
	public ArrayList<Course> selectAllUnEnrolledCourses(String sid) throws Exception{
		String sql = " "
				+ "SELECT * FROM course WHERE course.courseNumber NOT IN ( "
				+ "	SELECT course.courseNumber FROM course "
				+ "	JOIN enroll ON course.courseNumber = enroll.courseNumber "
				+ "	WHERE enroll.sid = ? "
				+ "); "
				+ "";
		ArrayList<Course> result = null;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			rs = ptmt.executeQuery();
			result = new ArrayList<Course>();
			while (rs.next()) {
				result.add(new Course(rs));
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}
	public ArrayList<Course> selectAllUnenrolledCourses(String sid, int enrollSemester) throws Exception{
		String sql = "SELECT * FROM course WHERE courseNumber NOT IN ( "
				+ "SELECT course.courseNumber FROM course "
				+ "JOIN enroll ON course.courseNumber = enroll.courseNumber "
				+ "JOIN student ON enroll.sid = student.sid "
				+ "WHERE student.sid = ? "
				+ ") "
				+ "AND (course.recommendSemester MOD 2 = ? MOD 2 OR course.recommendSemester = 0)";
		System.out.println(sql);
		ArrayList<Course> result = null;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			ptmt.setInt(2, enrollSemester);
			rs = ptmt.executeQuery();
			result = new ArrayList<Course>();
			while (rs.next()) {
				result.add(new Course(rs));
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}

	public ArrayList<Course> getOpenCourses(String sid) throws Exception {
		String sql = "SELECT ac, name, recommendSemester, credit, mandatoryCode,detail, isElective,description types FROM ( "
				+ "	SELECT courseNumber ac, name, recommendSemester, credit, mandatoryCode, isElective,description, MID(types, 6) detail, types, ( "
				+ "		SELECT count(*) FROM enroll WHERE courseNumber IN( "
				+ "			SELECT * FROM ( "
				+ "				SELECT ParentCourse FROM prerequiste WHERE ChildCourse = ac "
				+ "			) A) "
				+ "		AND enroll.sid = ?  "
				+ "	)my, ( "
				+ "		SELECT count(*) need FROM prerequiste WHERE ChildCourse = ac "
				+ "	) need "
				+ "	FROM course WHERE courseNumber NOT IN ( "
				+ "		SELECT course.courseNumber FROM course "
				+ "		JOIN enroll ON course.courseNumber = enroll.courseNumber "
				+ "		WHERE enroll.sid = ? "
				+ "	) "
				+ ")TT WHERE my >= need; ";
		ArrayList<Course> result = null;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			ptmt.setString(2, sid);
			rs = ptmt.executeQuery();
			result = new ArrayList<Course>();
			while (rs.next()) {
				Course course = new Course();
				course.setCourseNumber(rs.getString("ac"));
				course.setName(rs.getString("name"));
				course.setCredit(rs.getInt("credit"));
				course.setRecommendSemester(rs.getInt("recommendSemester"));
				course.setIsElective(rs.getBoolean("isElective"));
				course.setType(rs.getString("types"));
				course.setMandatoryCode(rs.getString("mandatoryCode"));
				result.add(course);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}

	public ArrayList<Course> getCloseCourses(String sid) throws Exception {
		String sql = "SELECT ac, name, recommendSemester, credit, mandatoryCode,detail, isElective,description, types FROM ( "
				+ "	SELECT courseNumber ac, name, recommendSemester, credit, mandatoryCode, isElective, description, MID(types, 6) detail, types, ( "
				+ "		SELECT count(*) FROM enroll WHERE courseNumber IN( "
				+ "			SELECT * FROM ( "
				+ "				SELECT ParentCourse FROM prerequiste WHERE ChildCourse = ac "
				+ "			) A) "
				+ "		AND enroll.sid = ?  "
				+ "	)my, ( "
				+ "		SELECT count(*) need FROM prerequiste WHERE ChildCourse = ac "
				+ "	) need "
				+ "	FROM course WHERE courseNumber NOT IN ( "
				+ "		SELECT course.courseNumber FROM course "
				+ "		JOIN enroll ON course.courseNumber = enroll.courseNumber "
				+ "		WHERE enroll.sid = ? "
				+ "	) "
				+ ")TT WHERE my < need; ";
		ArrayList<Course> result = null;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			ptmt.setString(2, sid);
			rs = ptmt.executeQuery();
			result = new ArrayList<Course>();
			while (rs.next()) {
				Course course = new Course();
				course.setCourseNumber(rs.getString("ac"));
				course.setName(rs.getString("name"));
				course.setCredit(rs.getInt("credit"));
				course.setRecommendSemester(rs.getInt("recommendSemester"));
				course.setIsElective(rs.getBoolean("isElective"));
				course.setType(rs.getString("types"));
				course.setMandatoryCode(rs.getString("mandatoryCode"));
				
				result.add(course);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}
	public String getSuccessTagString(String sid) throws SQLException{
		try {
			ArrayList<String> success = new ArrayList<String>();
			HashMap<String ,ArrayList<Course>> selectMandatory = selectSelectMandatoryCourse(sid);
			Iterator<Entry<String, ArrayList<Course>>> iter = selectMandatory.entrySet().iterator();
			while(iter.hasNext()) {
				Entry<String, ArrayList<Course>> entry = iter.next();
				if (entry.getKey().equals("과학")) {
					if (entry.getValue().size() >= 2) success.add("과학");
				}
				else if(entry.getKey().equals("리더십")) {
					if (entry.getValue().size() >= 1) success.add("리더십");
				}
				else if (entry.getKey().equals("설계")) {
					if (entry.getValue().size() >= 2) success.add("설계");
				}else if (entry.getKey().equals("BSM")) {
					ArrayList<Course> courses = entry.getValue();
					int credits = 0;
					Iterator<Course> gather = courses.iterator();
					while (gather.hasNext()) {
						credits += gather.next().getCredit();
					}
					if (credits >= 21) success.add("BSM ");
				}
			}
			String suc = "\'";
			Iterator<String> su = success.iterator();
			while (su.hasNext()) {
				suc += su.next();
				if (su.hasNext()) suc += "\', \'";
			}
			suc += "\'";
			System.out.println(suc);
			return suc;
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	public HashMap<String, ArrayList<Course>> selectSelectMandatoryCourse(String sid) throws SQLException{
		HashMap<String, ArrayList<Course>> result = null;
		String sql = "SELECT course.courseNumber, name, credit, LEFT(types,5) detail1, MID(types, 6) detail2 FROM course JOIN enroll "
				+ "ON course.courseNumber = enroll.courseNumber "
				+ "WHERE course.mandatoryCode = 'C' and enroll.sid = ?";
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			rs = ptmt.executeQuery();
			result = new HashMap<String, ArrayList<Course>>();
			while (rs.next()) {
				String detail1 = rs.getString("detail1");
				String detail2 = rs.getString("detail2");
				String name = rs.getString("name");
				int	credit = rs.getInt("credit");
				String cid = rs.getString("course.courseNumber");
				Course c1 = new Course(cid, name, credit, detail1);
				Course c2 = new Course(cid, name, credit, detail2);
				if(result.get(detail1) == null) {
					result.put(detail1, new ArrayList<Course>());
				}
				if(detail2 != null) {
					if( result.get(detail2) == null) {
						result.put(detail2, new ArrayList<Course>());
					}
					result.get(detail2).add(c2);
				}
				result.get(detail1).add(c1);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}


	public ArrayList<Course> selectRecommendMandatoryCourse(String sid, int curSemester, String suc) throws SQLException{
		try{
			String sql = " "
					+ "SELECT recommendID, name, recommendSemester, credit, mandatoryCode,detail1, detail2 FROM ( "
					+ "	SELECT course.courseNumber recommendID, name, recommendSemester, credit, mandatoryCode, LEFT(types,5) detail1, MID(types, 6) detail2, ( "
					+ "		SELECT count(*) FROM enroll WHERE courseNumber IN( "
					+ "			SELECT * FROM ( "
					+ "				SELECT ParentCourse FROM prerequiste WHERE ChildCourse = recommendID "
					+ "			) A) "
					+ "		AND enroll.sid = ? "
					+ "	)my, ( "
					+ "		SELECT count(*) need FROM prerequiste WHERE ChildCourse = recommendID "
					+ "	) need "
					+ "	FROM course "
					+ ")TT "
					+ "WHERE my >= need AND "
					+ "( "
					+ "	mandatoryCode = 'M' "
					+ "	OR "
					+ "	( "
					+ "		mandatoryCode = 'C' ";
			if (!suc.equals("''")) {
					sql = sql 
					+ "		AND "
					+ "		detail1 NOT IN ( "
					+ "			"+ suc + " "
					+ "		) "
					+ "		AND "
					+ "		detail2 NOT IN ( "
					+ "			"+ suc + " "
					+ "		) ";
			}
					sql = sql 
					+ "	) "
					+ ") AND recommendID NOT IN "
					+ "( "
					+ "	SELECT course.courseNumber FROM enroll JOIN course "
					+ "	WHERE enroll.courseNumber = course.courseNumber AND enroll.sid = ? "
					+ ") AND recommendSemester <= ? "
					+ "ORDER BY recommendSemester";
			conn = getConnection();
			System.out.println(sql);
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			ptmt.setString(2, sid);
//			ptmt.setString(2, suc);
			ptmt.setInt(3, curSemester);
			rs = ptmt.executeQuery();
			ArrayList<Course> result = new ArrayList<Course>();
			while (rs.next()) {
				Course course = new Course();
				course.setCourseNumber(rs.getString("recommendID"));
				course.setName(rs.getString("name"));
				course.setRecommendSemester(rs.getInt("recommendSemester"));
				course.setCredit(rs.getInt("credit"));
				course.setMandatoryCode("mandatoryCode");
				course.setDetail(rs.getString("detail1") + " " + rs.getString("detail2"));
				result.add(course);
			}
			for (int i = 0 ; i < result.size(); i++) {
				Course head = result.get(0);
				if (result.get(0).getRecommendSemester() == 0) {
					result.remove(0);
					result.add(head);
				}
			}
			return result;
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally{
			close(conn, ptmt, rs);
		}
	}


	public ArrayList<Course> selectRecommendSelectiveCourse(String sid, int curSemester, String suc) throws SQLException{
		try{
			String sql = "SELECT recommendID, name, recommendSemester, credit, mandatoryCode, detail1, detail2 FROM ( "
					+ "	SELECT course.courseNumber recommendID, name, recommendSemester, credit, mandatoryCode, LEFT(types,5) detail1, MID(types, 6) detail2, ( "
					+ "		SELECT count(*) FROM enroll WHERE courseNumber IN( "
					+ "			SELECT * FROM ( "
					+ "				SELECT ParentCourse FROM prerequiste WHERE ChildCourse = recommendID "
					+ "			) A) "
					+ "		AND enroll.sid = ? "
					+ "	)my, ( "
					+ "		SELECT count(*) need FROM prerequiste WHERE ChildCourse = recommendID "
					+ "	) need "
					+ "	FROM course "
					+ ")TT "
					+ "WHERE my >= need AND "
					+ "( "
					+ "	mandatoryCode = 'S' ";
			if (!suc.equals("''")) {
					sql = sql 
					+ "		AND "
					+ "		detail1 NOT IN ( "
					+ "			"+ suc + " "
					+ "		) "
					+ "		AND "
					+ "		detail2 NOT IN ( "
					+ "			"+ suc + " "
					+ "		) ";
			}
					
					sql = sql 
					+ ") AND recommendID NOT IN "
					+ "( "
					+ "	SELECT course.courseNumber FROM enroll JOIN course "
					+ "	WHERE enroll.courseNumber = course.courseNumber AND enroll.sid = ? "
					+ ") AND recommendSemester <= ? "
					+ "ORDER BY recommendSemester; ";
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
//			ptmt.setString(2, suc);
			ptmt.setString(2, sid);
			ptmt.setInt(3, curSemester);
			rs = ptmt.executeQuery();
			ArrayList<Course> result = new ArrayList<Course>();
			while (rs.next()) {
				Course course = new Course();
				course.setCourseNumber(rs.getString("recommendID"));
				course.setName(rs.getString("name"));
				course.setRecommendSemester(rs.getInt("recommendSemester"));
				course.setCredit(rs.getInt("credit"));
				course.setMandatoryCode("mandatoryCode");
				course.setDetail(rs.getString("detail1") + " " + rs.getString("detail2"));
				result.add(course);
			}
			for (int i = 0 ; i < result.size(); i++) {
				Course head = result.get(0);
				if (result.get(0).getRecommendSemester() == 0) {
					result.remove(0);
					result.add(head);
				}
			}
			return result;
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally{
			close(conn, ptmt, rs);
		}
	}
	public ArrayList<Course> selectAllCourses(String sid) throws SQLException{
		String sql = "SELECT A.sid, A.courseNumber, grade, credit, recommendSemester, isElective, mandatoryCode,enrollYear, enrollSemester,description,types, name FROM ( "
				+ "SELECT course.courseNumber, course.name, enroll.sid, enroll.grade, course.credit, recommendSemester, isElective, mandatoryCode, enrollYear, enrollSemester,description, types "
				+ " FROM course LEFT JOIN enroll ON course.courseNumber = enroll.courseNumber "
				+ ")A WHERE (sid = ? OR sid IS null)";
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			rs = ptmt.executeQuery();
			ArrayList<Course> result = new ArrayList<Course>();
		while (rs.next()) {
			result.add(new Course(
					rs.getString("courseNumber"),
					rs.getString("name"),
					rs.getInt("credit"),
					rs.getInt("recommendSemester"),
					rs.getString("mandatoryCode"),
					rs.getBoolean("isElective"),
					rs.getString("types"),
					rs.getString("grade"),
					rs.getString("description"),
					rs.getInt("enrollYear"),
					rs.getInt("enrollSemester")
					));
			}
		System.out.println(result.size());
		return result;
		}catch(SQLException e){
			e.printStackTrace();
			throw e;
		}finally{
			close(conn, ptmt, rs);
		}
	}
	
	public int enrollCourse(String sid, String courseNumber, int enrollSemester, int enrollYear, int studentSemester) throws SQLException {
		String sql = "INSERT INTO enroll VALUES (?, ?, ?, ?, ?, ?)";
		int result = -1;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			ptmt.setString(2, courseNumber);
			ptmt.setString(3, "I");
			ptmt.setInt(4, enrollSemester);
			ptmt.setInt(5, enrollYear);
			ptmt.setInt(6, studentSemester);
			result = ptmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}

	public int enrollCourse(String sid, String courseNumber, String grade, int enrollSemester, int enrollYear, int studentSemester) throws SQLException {
		String sql = "INSERT INTO enroll VALUES (?, ?, ?, ?, ?, ?)";
		int result = -1;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			ptmt.setString(2, courseNumber);
			ptmt.setString(3, grade);
			ptmt.setInt(4, enrollSemester);
			ptmt.setInt(5, enrollYear);
			ptmt.setInt(6, studentSemester);
			result = ptmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}
	public int insertCourses(String sid, ArrayList<Course> courses) throws SQLException {
		String sql = "INSERT INTO enroll VALUES ";
		int result = 0;
		for (int i = 0 ; i < courses.size(); i++) {
			sql += " (?, ?, ?, ?, ?, ?)";
			if (i != courses.size() - 1) {
				sql += ", ";
			}
		}
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			for (int i = 0 ; i < courses.size(); i++) {
				Course course = courses.get(i);
				ptmt.setString(1 + i * 6, sid);
				ptmt.setString(2 + i * 6, course.getCourseNumber());
				ptmt.setString(3 + i * 6, course.getGrade());
				ptmt.setInt   (4 + i * 6, course.getEnrollSemester());
				ptmt.setInt   (5 + i * 6, course.getEnrollYear());
				ptmt.setInt   (6 + i * 6, course.getStudentSemester());
			}
			if (courses.size() != 0) 
				result = ptmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}
	
	public int deleteCourses(String sid, ArrayList<Course> courses) throws SQLException{
		String sql  = "DELETE FROM enroll WHERE ";
		int result = 0;
		for (int i = 0 ; i < courses.size(); i++) {
			sql += "(sid = ? AND courseNumber = ? AND studentSemester = ?)";
			if (i != courses.size() - 1) {
				sql += "OR";
			}
		}
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			for (int i = 0 ; i < courses.size(); i++) {
				Course course = courses.get(i);
				ptmt.setString(1 + i * 3, sid);
				ptmt.setString(2 + i * 3, course.getCourseNumber());
				ptmt.setInt   (3 + i * 3, course.getStudentSemester());
			}
			if (courses.size() != 0) 
				result = ptmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}
	
	public int updateCourses(String sid, ArrayList<Course> courses) throws SQLException {
		int del = deleteCourses(sid, courses);
		int ins = insertCourses(sid, courses);
		if (del == ins) {
			System.out.println("update complete");
		}
		return ins;
	}
	
	public int fetchCourse(String sid, String courseNumber, String grade, int enrollSemester, int enrollYear, int studentSemester) throws SQLException {
		String sql = "UPDATE enroll SET grade = ? WHERE sid = ? AND courseNumber = ? AND enrollSemester = ? AND enrollYear = ?";
		int result = -1;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, grade);
			ptmt.setString(2, sid);
			ptmt.setString(3, courseNumber);
			ptmt.setInt(4, enrollSemester);
			ptmt.setInt(5, enrollYear);
			result = ptmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}
	public int unenrollCourse(String sid, String courseNumber, int studentSemester) throws SQLException {
		String sql = "DELETE FROM enroll WHERE sid = ? AND courseNumber = ? AND studentSemester = ?";
		int result = -1;
		try {
			conn = getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, sid);
			ptmt.setString(2, courseNumber);
			ptmt.setInt(3, studentSemester);
			result = ptmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}finally {
			close(conn, ptmt, rs);
		}
		return result;
	}
}
