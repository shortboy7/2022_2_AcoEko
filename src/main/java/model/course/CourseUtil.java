package model.course;

public class CourseUtil {
	/* 
	 * @param I,P,F인 course는 입력하지 않는다.
	 * */
	public static double getScore(Course course) {
		String grade = course.getGrade();
		double score = 0.0;
		if (grade.equals("A+")) score = 4.5;
		else if(grade.equals("A")) score = 4;
		else if(grade.equals("B+")) score =3.5;
		else if(grade.equals("B")) score = 3;
		else if(grade.equals("C+")) score = 2.5;
		else if(grade.equals("C")) score = 2;
		else if(grade.equals("D+")) score = 1.5;
		else if(grade.equals("D")) score = 1;
		else score = 0.0;
		return score;
	}

	public static double getScore(String grade) {
		double score = 0.0;
		if (grade.equals("A+")) score = 4.5;
		else if(grade.equals("A")) score = 4;
		else if(grade.equals("B+")) score =3.5;
		else if(grade.equals("B")) score = 3;
		else if(grade.equals("C+")) score = 2.5;
		else if(grade.equals("C")) score = 2;
		else if(grade.equals("D+")) score = 1.5;
		else if(grade.equals("D")) score = 1;
		else score = 0.0;
		return score;
	}
}
