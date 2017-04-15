package shoppingknowledge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class KnowledgeEngine {
	private int delCost, insCost, subCost;
	private ArrayList<String> knownwords;
	
	public KnowledgeEngine() {
		this("wordlist.txt", 1, 1, 1);
	}
	
	public KnowledgeEngine(String filename, int delCost, int insCost, int subCost) {
		this.delCost = delCost;
		this.insCost = insCost;
		this.subCost = subCost;
		
		knownwords = new ArrayList<String>(); //using this for now
		
		try (Scanner reader = new Scanner(new FileReader("wordlist.txt"))) {
			String line = null;
		    while (reader.hasNextLine()) {
		        knownwords.add(reader.nextLine());
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int levenshteinDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();		
		int m = s1.length() + 1;
		int n = s2.length() + 1;
		
		int[][] costs = new int[m][n];
		
		for (int i=0; i<m; i++)
			costs[i][0] = i;
		
		for (int i=0; i<n; i++)
			costs[0][i] = i;
		
		int thisCost;
		for (int j=1; j<n; j++) {
			for (int i=1; i<m; i++) {
				thisCost = (s1.charAt(i-1) == s2.charAt(j-1)) ? 0 : this.subCost;
				costs[i][j] = Math.min(costs[i-1][j] + this.delCost, Math.min(
						               costs[i][j-1] + this.delCost,
						               costs[i-1][j-1] + thisCost));
			}
		}
		
		return costs[m-1][n-1];
	}
	
	public String closest(String word) {
		int bestDist = Integer.MAX_VALUE;
		String closest = "";
		for (String knownWord : this.knownwords) {
			int dist = this.levenshteinDistance(word, knownWord);
			if (dist < bestDist) {
				bestDist = dist;
				closest = knownWord;
			}
		}
		
		return closest;
	}
}
