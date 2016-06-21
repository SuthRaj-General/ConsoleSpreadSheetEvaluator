//Created by: Sutharsan Rajaratnam
//Created Date: June 01, 2016
//Last Modified: June 06, 2016 

import java.io.*; 
import java.util.*;
import java.util.regex.*;
import java.text.DecimalFormat;

public class LoadSpreadSheet 
{
	private File inputFile;
	private Queue<String[]> q1 = new LinkedList<String[]>();
	private Queue<ForwardReference> q2 = new LinkedList<ForwardReference>();
	private String[][] outputArray;
	private ForwardReference objForwardRef;
	private BufferedReader br = null;
	private String csvStrDeliminate = ",";
	
	private String testInputFileName = "inputCSV.csv";
	private int csvColSize = 0;
	private int csvRowSize = 0;
	
	//outputs the results to Output.csv 
	private String outputFileName = "output.csv";
	
	public LoadSpreadSheet()
	{
		defaultLoadFileCSV();
	}
	

	public LoadSpreadSheet(String inputFileName)
	{
		LoadFileCSV(inputFileName);
	}
	

	
	//Load the user .csv file 
	public void LoadFileCSV(String inputFileName)
	{
		//inputFile = new File(inputFileName);
		parseFile(inputFileName);
	}
	
	
	//Load the default .csv file
	//Used for testing purposes only when the user does not provide privide a .csv when 
	private void defaultLoadFileCSV() //throws IOException
	{
		parseFile(testInputFileName);
	}
	
	/*Imports data from input data .csv file then stores each row in a Queue
	 * Additionally, displays/outputs the imported data onto the console
	 */
	
	private void parseFile(String inputFileName)
	{
		int i = 0;
		String readRowCSV ="";	
		String[] cellsOfRow = new String[]{};

		try
		{
			br = new BufferedReader(new FileReader(inputFileName));
			
			System.out.println("Spread Sheet Data ============= INPUT =============");
			while ( (readRowCSV= br.readLine()) != null)
			{
				//The 'split' function performs parsing of CSV row from file and returns the results as String[]
				cellsOfRow = readRowCSV.split(csvStrDeliminate);
				
				//push cellOfRow onto queue
				q1.add(cellsOfRow);
				
				for(i=0; i < cellsOfRow.length; i++)
				{
					System.out.printf("%11s", cellsOfRow[i]);
				}
				System.out.println("");
				csvRowSize++;
								
			}
			csvColSize = cellsOfRow.length;
			
			System.out.println("");
			System.out.println("# of Rows in SpreadSheet is = " + csvRowSize);
			System.out.println("# of Columns in SpreadSheet is = " + csvColSize);
			System.out.println("");
			
			rowEvaluate();
		}
		
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
					
				}
			}
		}
	}
	
	/*Method extracts each stored row of data from the queue and 
	 * performs appropriate operations 
	 */
	
	private void rowEvaluate()
	{
		int cellOpSize = 0;
		String[] rowOfCells = new String[] {};
		int i =0;
		int j =0;
		int tempRowIndex =0;// = csvRowSize;
		float outputCellVal = 0;
		String cell = "";
		DecimalFormat twoDForm = new DecimalFormat("#.#");
		

		outputArray = new String[csvRowSize][csvColSize];
		
		BufferedWriter output = null;
        try 
        {
        	File outputFile = new File(outputFileName); 
            output = new BufferedWriter(new FileWriter(outputFile));
            
			//Pop off from queue
			while (!q1.isEmpty())	
			{
				rowOfCells = q1.remove();
				//System.out.println("Queue not empty");
			
				for (i=0; i< csvColSize; i++)
				{
					cell = rowOfCells[i];
						outputCellVal = evalCell(cell, i, tempRowIndex);
			
							//System.out.println("Diagnostics: HERE");
						outputArray[tempRowIndex][i] = (twoDForm.format(outputCellVal));
						output.write(twoDForm.format(outputCellVal)+ ",");
				}
				
				output.newLine();
				tempRowIndex++;	
			}
        } 
        catch ( IOException e ) 
        {
            e.printStackTrace();
        } 
        finally 
        {
          if ( output != null ) 
          {
            try
            {
        	  output.close();
            }
            catch (Exception e)
            {
            	System.out.println("Error: Can't close output.csv file");
            }
          }
        }
		
		evalForwardReference();
		
		System.out.println("Spread Sheet Data ============= OUTPUT =============");
		for (i=0; i<csvRowSize; i++ )
		{
			for (j=0; j< csvColSize; j++)
			{
				//System.out.printf("%5s %.1f"," ", outputArray[i][j]);
				System.out.printf("%10s", outputArray[i][j]);
			}
			System.out.println(" ");
		}		
		
	}
	
	/*Method identifies and performs the necessary mathematical operations on the individual
	 * cells (including column references)
	 * PLEASE NOTE: Forward Cell Referencing capabilities are NOT implemented
	 */
	
	private float evalCell (String cell, int currCol, int currRow) 
	{
		int i =0;
		String[] cellOp = new String[] {};
		int cellOpSize=0;
		int colNum = 0;
		int rowNum = 0;
		String outputCellVal = "";
		
		//Variable used to identify Column references
		String pattern = "(^=)(\\D)(\\d)";
		ColumnMatch findColNum = new ColumnMatch();
		
		//Create a Pattern object
		Pattern regex = Pattern.compile(pattern);
		
		
		//if (cell.substring(0, 0).matches("=") )
		cellOp = cell.split(" ");
		
		cellOpSize = cellOp.length;

		try 
		{
			if ( !(cell.startsWith("=")) )	
			{
				//System.out.println("Diagnostics: HERE 1");
				outputCellVal = cell.substring(0);
			}
		
			else if ( (cellOpSize <= 1) && (cell.startsWith("=")) )
			{
				//System.out.println("Diagnostics: HERE 2");
			
				//Create matcher object
				//==================================================================================
				Matcher m = regex.matcher(cell);
			

				if(m.find())
				{
					String colName = cell.substring(1, 2);
					//System.out.println("Diagnostics: Pattern found!");
					colNum = findColNum.getHashValue(colName);
					rowNum = Integer.valueOf(cell.substring(2)) - 1;
					//System.out.println("Diagnostics: Column Num.... " + colNum);
				
					
					//Forward cell reference calls are added to a queue for evaluation after all cells have been
					//identified. Forward cell reference are evaluated in the end after going through all cell assignments.  
					if ( (rowNum > currRow) || ( (rowNum == currRow)&&(colNum > currCol) ) )
					{
						objForwardRef = new ForwardReference(currRow, currCol, rowNum, colNum);
						q2.add(objForwardRef);
						outputCellVal = "0";
					}
						
					else
					{
						try
						{
							outputCellVal = outputArray[rowNum][colNum];
						}
						catch(Exception e)
						{
							System.out.println("ERROR 1");
						}
				
					//System.out.println(cell.substring(0, 1));
					//System.out.println(cell.substring(1, 2));
					//System.out.println(cell.substring(2));
					}
				}
			
				else
					outputCellVal = cell.substring(1);	
				}
		
			else if ( (cellOpSize >= 1) && (cell.startsWith("=")) )
			{
				//System.out.println("Diagnostics: HERE 3");
				outputCellVal = calcOp(cellOp, cellOpSize);			
			}
			else
			{
				//System.out.println("Diagnostics: HERE 4");
				outputCellVal = "0";
			}
		}
		catch ( Exception e)
		{
			System.out.println("ERROR 2");
		}

		return Float.parseFloat( outputCellVal.trim() );
	}
	
	private String calcOp(String[] cellOp, int cellOpSize)
	{
		float outputCellVal = 0;
		String mathOp = null;
		int i = 0;
		
		
		if(cellOp[0].startsWith("=") && (cellOpSize < 1))
		{
			outputCellVal = Float.valueOf(cellOp[0]);
		}
		
		else if(cellOp[0].startsWith("=") && (cellOpSize > 1))
		{
			mathOp = cellOp[cellOpSize-1];
			//System.out.println(mathOp);
			
			switch (mathOp)
			{
				case "+":	
					outputCellVal = Float.valueOf(cellOp[0].substring(1)) + Float.valueOf(cellOp[1]);
					break;
				case "-":	
					outputCellVal = Float.valueOf(cellOp[0].substring(1)) - Integer.valueOf(cellOp[1]);
					break;
				case "*":	
					outputCellVal = Float.valueOf(cellOp[0].substring(1)) * Float.valueOf(cellOp[1]);
					break;
				case "/":	
					outputCellVal = Float.valueOf(cellOp[0].substring(1)) / Float.valueOf(cellOp[1]);
					break;
				default: outputCellVal = 0;
										
			}
		}
		else
		{
			System.out.println("Error: Cell did NOT evaluate correctly!!!");
		}
		
		return Float.toString(outputCellVal);
	}
	 

//Method processes the "forward cell reference" queue and assigns appropriate values
	private void evalForwardReference()
	{
		int currRowIndex = 0;
		int currColIndex = 0;
		int forRefRowIndex = 0;
		int forRefColIndex = 0;
		int i = 0;
		
		if (!q2.isEmpty())
		{
			System.out.println("NOTE: Spread Sheet contained forward cell references! ");
		}
		
		while (!q2.isEmpty())
		{
			ForwardReference forRef = q2.remove();
			
			currRowIndex = forRef.getCurrRowIndex();
			currColIndex = forRef.getCurrColumnIndex();
			forRefRowIndex = forRef.getForRefRowIndex();
			forRefColIndex = forRef.getForRefColumnIndex();
			
			outputArray[currRowIndex][currColIndex] = outputArray[forRefRowIndex][forRefColIndex];
			
		}
	}

}
