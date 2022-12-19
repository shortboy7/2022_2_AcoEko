package model.course;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;

import general.JSONable;
public class Course implements JSONable{
	public String getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String geteMandatoryCode() {
		return mandatoryCode;
	}
	public void setMandatoryCode(String mandatoryCode) {
		this.mandatoryCode = mandatoryCode;
	}
	public boolean isElective() {
		return isElective;
	}
	public void setIsElective(boolean isElective) {
		this.isElective = isElective;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getRecommendSemester() {
		return recommendSemester;
	}
	public void setRecommendSemester(int recommendSemester) {
		this.recommendSemester = recommendSemester;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEnrollSemester() {
		return enrollSemester;
	}
	public void setEnrollSemester(int enrollSemester) {
		this.enrollSemester = enrollSemester;
	}
	public int getEnrollYear() {
		return enrollYear;
	}
	public void setEnrollYear(int enrollYear) {
		this.enrollYear = enrollYear;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getMandatoryCode() {
		return mandatoryCode;
	}
	public int getStudentSemester() {
		return studentSemester;
	}
	public void setStudentSemester(int studentSemester) {
		this.studentSemester = studentSemester;
	}
	public Course(ResultSet rs) throws SQLException {
		try {
			this.courseNumber = rs.getString("courseNumber");
			this.credit = rs.getInt("credit");
//			this.enrollSemester = rs.getInt("enrollSemester");
//			this.enrollYear = rs.getInt("enrollYear");
			this.mandatoryCode = rs.getString("mandatoryCode");
			this.isElective = rs.getBoolean("isElective");
			this.type = rs.getString("types");
			this.description = rs.getString("description");
			this.name = rs.getString("name");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	public Course(String courseNumber, String name, int credit, int recommendSemester, String mandatoryCode,
			boolean isElective, String type, String description, String grade, int enrollSemester,
			int enrollYear) {
		super();
		this.courseNumber = courseNumber;
		this.name = name;
		this.credit = credit;
		this.recommendSemester = recommendSemester;
		this.mandatoryCode = mandatoryCode;
		this.isElective = isElective;
		this.type = type;
		this.description = description;
		this.grade = grade;
		this.enrollSemester = enrollSemester;
		this.enrollYear = enrollYear;
	}
	public Course(String courseNumber, String name, int credit, int recommendSemester, String mandatoryCode,
			boolean isElective, String type, String description) {
		super();
		this.courseNumber = courseNumber;
		this.name = name;
		this.credit = credit;
		this.recommendSemester = recommendSemester;
		this.mandatoryCode = mandatoryCode;
		this.isElective = isElective;
		this.type = type;
		this.description = description;
	}
	public Course(String courseNumber, String name, int credit, String type) {
		super();
		this.courseNumber = courseNumber;
		this.name = name;
		this.credit = credit;
		this.type = type;
	}
	public Course() {}
	private String courseNumber;
	private String name;
	private int	credit;
	private int recommendSemester;
	private String mandatoryCode;
	private boolean isElective;
	public void setElective(boolean isElective) {
		this.isElective = isElective;
	}
	private	String type;
	private	String description;
	private String grade;
	private int enrollSemester;
	private int enrollYear;
	private int studentSemester;
	private String detail;

	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		object.put("id", courseNumber);
		object.put("credit", credit);
		object.put("name", name);
		object.put("grade", grade);
		if (grade == null) {
			object.put("score", 0);
		}else {
			object.put("score", CourseUtil.getScore(grade));
		}
		object.put("enrollYear", enrollYear);
		object.put("enrollSemester", enrollSemester);
		object.put("studentSemester", studentSemester);
		object.put("types", type);
		return object;
	}
}
