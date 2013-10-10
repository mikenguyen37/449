/**
 * CPSC449 Fall 2013 
 * @author team 3-3
 * 8 Oct 2013
 */

import java.io.*;

public class fileOutput 
{	

	public static void makeOutputFile(String outputName)	{
	
		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object
	
		try
		{
			// Create a new file output stream
			// connected to "<output file name>.txt"
			out = new FileOutputStream(outputName+".txt");
	
			// Connect print stream to the output stream
			p = new PrintStream( out );
			
			p.println ("Solution:\n");
			//p.println(Tree.assignment[] array???);
			p.println ("Quality:\n");
			//p.println(Tree.CalculateFinalCost(Assignment[] assignments)???//overall penalty);
			p.close();
		}
		catch (Exception e)
		{
			System.err.println ("Error writing to file");
		}
		
	}

}