package ecole.annee2.java.tp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

	
	//private GrapheMatrix monGraph = new GrapheMatrix();

	public Main() {

		//parseFileGraph();
		//System.out.println("---mon graph---"+monGraph);
		//calculCfc(monGraph);
		// parcoursProfondeur(monGraph,0);
		// cfc(monGraph);
		// afficherCfc(cfc(monGraph));
	}

	
	public static void main(String[] args) throws Exception {
		System.out.println("Mon main");
		File fGraph = new File("test3.txt");
		GrapheMatrix gm = new GrapheMatrix(fGraph);
		gm.getPCC_dijkstra("A");

	}

	

	


}
