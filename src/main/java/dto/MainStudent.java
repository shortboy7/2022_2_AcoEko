package dto;

import model.student.Student;

public class MainStudent {
	public int electiveSelectiveCredit = 0;
	public int electiveMandatoryCredit = 0;
	public int majorSelectiveCredit = 0;
	public int majorMandatoryCredit = 0;

	public int totalMajorCredit = 0;
	public int totalElectCredit = 0;

	public Double majorGradeAvg = null;
	public Double gradeAvg = null;

	public int totalCredit = 0;
	public int majorCredit = 0;

	public MainStudent() {
		electiveSelectiveCredit = 0;
		electiveMandatoryCredit= 0;
		majorSelectiveCredit = 0;
		majorMandatoryCredit = 0;

		totalMajorCredit = 0;
		totalElectCredit = 0;
		majorGradeAvg = new Double(0.0);
		gradeAvg = new Double(0.0);
	}
}
