import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * CPSC449 Fall 2013 
 * @author team 3-3
 * 8 Oct 2013
 * used this http://stackoverflow.com/questions/5287538/how-to-get-basic-user-input-for-java
 */


public class main 
{
	static String[] _args;
	public static List<String> tasks = new  ArrayList<String>();
	static int quality;
	public static Assignment[] bestSolution = null;
	
	public static void main(String[] args)
	{
		System.out.println("Hello and welcome to team 3-3's 449 a1, java version!" + '\n' );

		// check if the correct arguments are being used
		// myProgram myInput myOutput
		_args = args;
		if (args.length != 2)
		{
			System.out.println("Usage: <input file> <output file>");
			System.exit(0);
		}

		// run initial io class to obtain data for processing, stores data 
		// parse the input file with the first argument
		io.readInputFile(args[0]);
		//System.out.println(args[0] + " file has been read");
		

		Assignment[] lowestSolution = new Assignment[8];
		// put the best possible tasks in the unassigned lowestSolution[] elements
		for(int i = 0; i < lowestSolution.length; i++)
		{
			Assignment tempAssign = new Assignment();
			// find the lowest penalty for the machine
			int least = io.machinePenaltiesArray.get(i)[0];
			int n = 0; // n is the column number (task) with the smallest penalty
			for (int j = 1; j < io.machinePenaltiesArray.size(); j++)
			{
				//try{
				if(least > io.machinePenaltiesArray.get(i)[j])
				{
			        	least = io.machinePenaltiesArray.get(i)[j];
					n = j;
				}
				//}
				
				//catch (ArrayIndexOutOfBoundsException e){
				//	main.displayError(2);				
				//	}
			}
			tempAssign.mTask = Character.toString((char) (n+65)); //ascii value of n
			tempAssign.mForced = false;
			lowestSolution[i] = tempAssign;
		}
		// replace the forced assignments in lowestSolution[], if applicable
		for(int i = 0; i < io.forcedPartialAssignArray.size(); i++)
		{
			Assignment tempAssign = new Assignment();
			tempAssign.mTask = io.forcedPartialAssignArray.get(i).getRight();
			tempAssign.mForced = true;
			lowestSolution[io.forcedPartialAssignArray.get(i).getLeft()-1] = tempAssign;
		}

		Node node = new Node();
		node.mAssignnments = lowestSolution;
		Tree tree = new Tree();
		
		//YOU WANT THIS!!!!
		bestSolution = tree.CalculateBestSolution(node, 0);
		int bestCost = Calcs.CalculateFinalCost(bestSolution);
		//^^THATS WHAT YOU WANT

		if(bestSolution == null)
		{
			// this means there is no solution
			makeOutputFile(args[1], "No valid solution possible!");
			//System.out.println(args[1] + " outfile made");	
			return;
		}

		//For debugging
		for(int m = 0; m < lowestSolution.length; m++)
		{
			//System.out.println("Final Solution[" + m + "] = (" + bestSolution[m].mTask + "," + bestSolution[m].mForced + ")");	
			tasks.add(bestSolution[m].mTask);
		}
		
		// The lowest possible solution (taking into account forcedPartials and machine/task penalties is done. */

		// create the root node with lowestSolution[], an array of length lowestSolution for the child node pointers and caluclate the cost

		//System.out.println(bestCost);
	    quality = bestCost;

		String tempSolution = null;
		if(bestSolution !=null)
			{
				tempSolution = ("Solution");
				for (int i=0; i<tasks.size(); i++)
				{
					tempSolution = tempSolution.concat(" " + tasks.get(i));
				}
				tempSolution = tempSolution.concat("; Quality: " + quality);
			}
			else
			{
				//System.out.println("TESTSETSETSETSETSET");
				tempSolution = ("No valid solution possible!");
			}
		
	    
	    //create an output file named after the second argument
	    makeOutputFile(args[1], tempSolution);
		//System.out.println(args[1] + " output file created.");


	}
	
	public static void makeOutputFile(String outputName, String output)
	{
		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object

		try
		{
			// Create a new file output stream
			// connected to "<output file name>.txt"
			out = new FileOutputStream(outputName);

			// Connect print stream to the output stream
			p = new PrintStream( out );

			// Print the solution
			p.print (output);
			p.close();
		}
		catch (Exception e)
		{
			System.err.println ("Error writing to file");
		}
	}
		
	public static void calltoMakeOutputFile(String output)
	{
		makeOutputFile(_args[1], output);
	}
	
	public static void displayError(int errorNumber)
	{
		if (errorNumber == 1)
		{
			makeOutputFile(_args[1], "Error while parsing input file");
		}
		else if (errorNumber == 2)
		{
			makeOutputFile(_args[1], "machine penalty error");
		}
		else if (errorNumber == 3)
		{
			makeOutputFile(_args[1], "invalid machine/task");
		}
		else if (errorNumber == 4)
		{
			makeOutputFile(_args[1], "invalid task");
		}
		else if (errorNumber == 5)
		{
			makeOutputFile(_args[1], "invalid penalty");
		}
		// close program
		System.exit(0);
	}
}
