package apps;

import java.io.File;

import java.util.Scanner;
public class Driver {
	public static void main (String[] args)  throws Exception {
		File file = new File("graph1.txt");
		Scanner sc = new Scanner(file);
		Friends t = new Friends();
		Graph g = new Graph(sc);
		System.out.println(Friends.shortestChain (g, "d", "f"));
		System.out.println(Friends.cliques(g, "ucla"));
		System.out.println(Friends.connectors(g));
		
		sc.close();

	}
}