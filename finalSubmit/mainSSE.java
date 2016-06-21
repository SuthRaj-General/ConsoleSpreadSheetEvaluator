//Created by: Sutharsan Rajaratnam
//Created Date: June 01, 2016
//Last Modified: June 03, 2016 

/* The following program implements a "Console Spread Sheet Evaluator" 
 * Based on testing, almost all requirements have been satisfied except for the following:
 * PLEASE NOTE: Due to time constraints, Forward pointing Cell references are NOT implemented
 * If such references are in the input .csv file then a "NullPointer" exception error will be thrown 
 */

import java.io.*; 
import java.util.*;


public class mainSSE 
{

	private static LoadSpreadSheet createSS;
	
	public static void main(String[] args) 
	{
		System.out.println("User provided data file (.csv) detected (0: No, 1: Yes) " + args.length);
		
		try 
		{		
			initializeData(args.length, args);
		} 
		catch (IOException e) 
		{
			System.out.println("ERROR: Problems loading the input .csv file! \n");
		}

	}
	
	public static void initializeData(int argsLength, String args[])throws IOException
	{
		if ( 0 < argsLength )
		{
			System.out.println("--- Load Non-Default (user provided) Input file: " + args[0]); 
			System.out.println("");
			createSS = new LoadSpreadSheet(args[0]);
			 
		}
		else
		{
			System.out.println("--- Load Default Input file");
			System.out.println("");
			createSS = new LoadSpreadSheet();
			
		}
	}

}
