package util;

import java.util.ArrayList;

import model.course.Course;

public class MathHelper {
	private static void	select(ArrayList<Course> list, boolean[] visited, int start,int max, ArrayList<ArrayList<Course>> comb) {
		if (start == list.size()) {
			return;
		}
		if (comb.size() >= 5) return ; 

		 for (int i = start; i < list.size() ; i++) {
			 visited[i] = true;
			 int cur = 0;
			 for (int j = 0 ; j < visited.length; j++) {
				if (visited[j]) cur += list.get(j).getCredit();
			 }
			 if (cur < max) select(list, visited, i + 1,max, comb);
			 else if (cur == max) {
				ArrayList<Course> one = new ArrayList<Course>();
				for (int k = 0 ; k < visited.length; k++) {
					if (visited[k]) one.add(list.get(k));
				}
				comb.add(one);
			 }
			 if (comb.size() >= 5) return ; 
			 visited[i] = false;
		 }
	}
	public static ArrayList<ArrayList<Course>> makeCombination(ArrayList<Course> list, int maxCredit){
		ArrayList<ArrayList<Course>> comb = new ArrayList<ArrayList<Course>>();
		boolean [] visited = new boolean[list.size()];
		select(list, visited, 0, maxCredit, comb);
		return comb;
	}
}
