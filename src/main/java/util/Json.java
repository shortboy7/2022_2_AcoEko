package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import general.BaseResponse;
import general.JSONable;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;

import model.course.Course;
import model.student.Student;

public class Json {
	public static <T extends JSONable> JSONObject convertCollection(BaseResponse<ArrayList<T>> response) {
		JSONObject object = new JSONObject();
		object.put("success", response.getIsSuccess());
		object.put("message", response.getMessage());
		object.put("result", Json.convert(response.getResult()));
		return object;
	}
	public static <T extends JSONable> JSONObject convertMatrix(BaseResponse<ArrayList<ArrayList<T>>> response) {
		JSONObject object = new JSONObject();
		object.put("success", response.getIsSuccess());
		object.put("message", response.getMessage());
		ArrayList<ArrayList<T>> matrix = response.getResult();
		Iterator<ArrayList<T>> iter = matrix.iterator();
		int i = 0;
		JSONObject temp = new JSONObject();
		while (iter.hasNext()) {
			temp.put(i, convert(iter.next()));
			i++;
		}
		object.put("result", temp);
		return object;
	}
	public static <T extends JSONable> JSONObject convert(BaseResponse<T> response) {
		JSONObject object = new JSONObject();
		object.put("success", response.getIsSuccess());
		object.put("message", response.getMessage());
		if (response.getResult() != null)
			object.put("result", response.getResult().toJSONObject().toString());
		else object.put("result", "");
		return object;
	}
	public static <T extends JSONable> JSONArray convert(ArrayList<T> list) {
		JSONArray result = new JSONArray();
		if (list == null) return result;
		Iterator<T> iter = list.iterator();
		while(iter.hasNext()) {
			result.add(iter.next().toJSONObject());
		}
		return result;
	}
	public static JSONObject convertInteger(BaseResponse<Integer> response) {
		JSONObject object = new JSONObject();
		object.put("success", response.getIsSuccess());
		object.put("message", response.getMessage());	
		object.put("result", response.getResult());
		return object;
	}
	public static ArrayList<Course> extractArr(JSONObject obj, String key){
		JSONArray arr = (JSONArray) obj.get(key);
		ArrayList<Course> courses = new ArrayList<Course>();
		for (int i = 0 ; i < arr.size(); i++) {
			Course course = new Course();
			JSONObject object = (JSONObject) arr.get(i);
			course.setCourseNumber((String)object.get("id"));
			course.setStudentSemester(Integer.parseInt((String)object.get("studentSemester")));
			course.setGrade((String)object.get("grade"));
			course.setEnrollYear(Integer.parseInt((String)object.get("enrollYear")));
			course.setEnrollSemester(Integer.parseInt((String)object.get("enrollSemester")));
			
//			course.setCredit((int)object.get("credit"));
//			course.setName((String)object.get("name"));
			courses.add(course);
		}
		return courses;
	}
	public static JSONObject parseJSON(HttpServletRequest request) throws Exception {
		String json = "";
		String token = "";
		JSONObject obj = null;
		JSONParser parser = new JSONParser();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while (token != null) {
				token = br.readLine();
				if (token == null) break;
				json += token;
			}
			obj = (JSONObject) parser.parse(json);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return obj;
	}
	
}