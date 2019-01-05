package apps;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 *         
	 *        
	 */

		public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		HashMap<String, String> predecessorMap = new HashMap <String, String>();
		ArrayList<String> result = new ArrayList<String>();
		boolean found=false;
		if (p1.equals(p2)) {
			result.add(p1);
			return result;
		}
		int person1Address = g.map.get(p1);

		Queue<Person> q= new Queue<Person>();
		q.enqueue(g.members[person1Address]);
		int[] distances = new int[g.members.length];
		for (int x=0; x<distances.length; x++) {
			distances[x]= -1;
		}
		distances[person1Address]=0;
		while (!q.isEmpty()) {
			Person current = q.dequeue();
			Friend ptr = current.first;
			while (ptr != null) {
				if (distances[ptr.fnum] == -1) { 
					distances[ptr.fnum] = 0;
					q.enqueue(g.members[ptr.fnum]);
					String newName = g.members[ptr.fnum].name;
					predecessorMap.put(newName, current.name); //adds the friend's name to the results arraylist

				}
				if (g.members[ptr.fnum].name.equals(p2)) {
					found = true;
					String newName = g.members[ptr.fnum].name;
					predecessorMap.put(newName, current.name);
					break;

				}


				ptr = ptr.next;

			}
			if (found) {
				break;
			}


		}
		if (!found) {
			return null;
		}

		String temp = p2;
		result.add(p2);
		while (!result.contains(p1)) {
			result.add(predecessorMap.get(temp));
			temp=predecessorMap.get(temp);

		}
		Collections.reverse(result);
		return result;



	}
	 

	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */



	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) { 
		ArrayList<ArrayList<String>> cliques = islandFinder(g, school, null);
		return cliques;
	}



	private static boolean checkIfValid (Person p, String school, String nameToIgnore) {
		if  ((school == null || (p.student && p.school.equals(school))) && (nameToIgnore==null || (!p.name.equals(nameToIgnore)))) {
			//System.out.println(p.student + " " + p.school + " " + p.name);
			return true;
		}
		if (school != null && school.equals("") && !p.student) {
			return true;

		}
		return false;
	}

	private static ArrayList<ArrayList<String>> islandFinder (Graph g, String school, String nameToIgnore) {
		ArrayList<ArrayList<String>> cliques = new ArrayList<ArrayList<String>>();
		for (int k =0; k<g.members.length; k++) {
			int alreadyInClique = 0;
			if (!checkIfValid(g.members[k], school, nameToIgnore)){ //if they're not a student, or if they are a student but at another school, or if it's the person you're leaving out 
				continue;
			}
			for (int x=0; x<cliques.size(); x++) {
				if (cliques.get(x).contains(g.members[k].name)) {
					alreadyInClique++;
					break;
				}


			}
			if (alreadyInClique > 0) {
				continue;
			}
			ArrayList<String> temp = new ArrayList<String>();
			Queue<Person> q= new Queue<Person>();
			q.enqueue(g.members[k]);
			temp.add(g.members[k].name);
			int[] distances = new int[g.members.length];
			for (int x=0; x<distances.length; x++) {
				distances[x]= -1;
			}
			distances[k]=0;
			while (!q.isEmpty()) {
				Person current = q.dequeue();
				Friend ptr = current.first;
				while (ptr != null) {
					if (distances[ptr.fnum] == -1 && checkIfValid(g.members[ptr.fnum], school, nameToIgnore)) { 
						//System.out.println(g.members[ptr.fnum].name);
						distances[ptr.fnum] = 0;
						q.enqueue(g.members[ptr.fnum]);

						String newName = g.members[ptr.fnum].name;
						temp.add(newName); //adds the friend's name to the results arraylist

					}

					ptr = ptr.next;

				}


			}
			cliques.add(temp);


		}


		return cliques;

	}

	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		ArrayList<ArrayList<String>> withConnector = islandFinder (g, null, null);
		ArrayList<String> result = new ArrayList<String>();
		for (int k =0; k<g.members.length; k++) {

			ArrayList<ArrayList<String>> withoutConnector = islandFinder (g, null, g.members[k].name);
			//System.out.println(withConnector);
			//System.out.println(withoutConnector);


			if (withoutConnector.size() > withConnector.size()) {
				result.add(g.members[k].name);
			}

		}
		return result;
	}
}
