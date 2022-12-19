package model.student;

import org.json.simple.JSONObject;

import general.JSONable;

public class Student implements JSONable{
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getStnum() {
		return stnum;
	}
	public void setStnum(String stnum) {
		this.stnum = stnum;
	}
	public Student(
			String sid,
			String stnum,
			String pw,
			String name,
			int semester,
			String major,
			String phoneNumber
			) {
		super();
		this.sid = sid;
		this.stnum = stnum;
		this.pw = pw;
		this.name = name;
		this.semester = semester;
		this.major = major;
		this.phoneNumber = phoneNumber;
	}
	public JSONObject toJSONObject() {
		JSONObject object = new JSONObject();
		object.put("이름", name);
		object.put("전화번호", phoneNumber);
		object.put("학기", semester);
		object.put("전공", major);
		return object;
	}
	public int getMandatoryMajorCredit() {
		return mandatoryMajorCredit;
	}
	public void setMandatoryMajorCredit(int mandatoryMajorCredit) {
		this.mandatoryMajorCredit = mandatoryMajorCredit;
	}
	public int getSelectiveMajorCredit() {
		return selectiveMajorCredit;
	}
	public void setSelectiveMajorCredit(int selectiveMajorCredit) {
		this.selectiveMajorCredit = selectiveMajorCredit;
	}
	public int getMandatoryElectCredit() {
		return mandatoryElectCredit;
	}
	public void setMandatoryElectCredit(int mandatoryElectCredit) {
		this.mandatoryElectCredit = mandatoryElectCredit;
	}
	public int getSelectiveElectCredit() {
		return selectiveElectCredit;
	}
	public void setSelectiveElectCredit(int selectiveElectCredit) {
		this.selectiveElectCredit = selectiveElectCredit;
	}
	public double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
	public double getTotalMajorScore() {
		return totalMajorScore;
	}
	public void setTotalMajorScore(double totalMajorScore) {
		this.totalMajorScore = totalMajorScore;
	}
	public Student() {}
	private String	sid;
	private String	stnum;
	private String	pw;
	private String	name;
	private int		semester;
	private String	major;
	private String	phoneNumber;
	private	int		mandatoryMajorCredit = 0;
	private	int		selectiveMajorCredit = 0;
	private	int		mandatoryElectCredit = 0;
	private	int		selectiveElectCredit = 0;
	private	double	totalScore = 0;
	private	double	totalMajorScore = 0;
}
