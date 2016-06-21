//Created by: Sutharsan Rajaratnam
//Created Date: June 01, 2016
//Last Modified: June 03, 2016 

/* The class is used to match up column letters with their corresponding numerical column value
 * A Hashmap is used to achieve this 
 */

import java.io.*; 
import java.util.*;

public class ColumnMatch 
{
	private HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	private String findColNum = null;
	
	public ColumnMatch()
	{		
		//Populate HashMap with 'key' and corresponding 'value'
		hmap.put("A", 0);
		hmap.put("B", 1);
		hmap.put("C", 2);
		hmap.put("D", 3);
		hmap.put("E", 4);
		hmap.put("F", 5);
		hmap.put("G", 6);
		hmap.put("H", 7);
		hmap.put("I", 8);
		hmap.put("J", 9);
		hmap.put("K", 10);
		hmap.put("L", 11);
		hmap.put("M", 12);
		hmap.put("N", 13);
		hmap.put("O", 14);
		hmap.put("P", 15);
		hmap.put("Q", 16);
		hmap.put("R", 17);
		hmap.put("S", 18);
		hmap.put("T", 19);
		hmap.put("U", 20);
		hmap.put("V", 21);
		hmap.put("W", 22);
		hmap.put("X", 23);
		hmap.put("Y", 24);
		hmap.put("Z", 25);
	}
	
	public int getHashValue(String findColNum)
	{
		//System.out.println("Diagnostics: Matching Column name to num.... " + findColNum);
		int colNum = hmap.get(findColNum);
		return colNum;
	}

}
