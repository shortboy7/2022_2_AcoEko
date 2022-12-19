package dto;

import java.util.ArrayList;
import java.util.HashMap;

import model.course.Course;

public class RoadMap {
	public ArrayList<Course> getGradedCourses() {
		return gradedCourses;
	}

	public void setGradedCourses(ArrayList<Course> gradedCourses) {
		this.gradedCourses = gradedCourses;
	}

	public ArrayList<Course> getEnrollingCourses() {
		return enrollingCourses;
	}

	public void setEnrollingCourses(ArrayList<Course> enrollingCourses) {
		this.enrollingCourses = enrollingCourses;
	}

	public ArrayList<Course> getOpenCourses() {
		return openCourses;
	}

	public void setOpenCourses(ArrayList<Course> openCourses) {
		this.openCourses = openCourses;
	}

	public ArrayList<Course> getCloseCourses() {
		return closeCourses;
	}

	public void setCloseCourse(ArrayList<Course> closeCourse) {
		this.closeCourses = closeCourse;
	}
	private ArrayList<Course> gradedCourses = null;
	private ArrayList<Course> enrollingCourses = null;
	private ArrayList<Course> openCourses = null;
	private ArrayList<Course> closeCourses = null;

	public RoadMap() {
	}
}
