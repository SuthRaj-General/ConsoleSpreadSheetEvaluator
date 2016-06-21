//Created by: Sutharsan Rajaratnam
//Created Date: June 06, 2016
//Last Modified: June 06, 2016

//Used to handle forward cell reference assignments

public class ForwardReference 
{
	private int currRowIndex = 0;
	private int currColIndex = 0;
	private int forRefRowIndex = 0;
	private int forRefColIndex = 0;
	
	
	public ForwardReference(int currRow, int currCol, int forRow, int forCol)
	{
		currRowIndex = currRow;
		currColIndex = currCol;
		forRefRowIndex = forRow;
		forRefColIndex = forCol;
	}
	
	public int getCurrRowIndex()
	{
		return currRowIndex;
	}
	
	public int getCurrColumnIndex()
	{
		return currColIndex;
	}
	
	public int getForRefRowIndex()
	{
		return forRefRowIndex;
	}
	
	public int getForRefColumnIndex()
	{
		return forRefColIndex;
	}
}
