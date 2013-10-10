/**
 * CPSC449 Fall 2013 
 * @author team 3-3
 * 8 Oct 2013
 */

import java.io.IOException;
import java.util.*;
import java.io.*;

public class io {
	
	// store all the constraints in lists
	public static List<Pair<Integer, String>> forcedPartialAssignArray = new ArrayList<Pair<Integer, String>>();
	public static List<Pair<Integer, String>> forbiddenMachineArray = new ArrayList<Pair<Integer, String>>();
	public static List<Pair<String, String>> tooNeartasksArray  = new ArrayList<Pair<String, String>>();
	public static List<String[] >machinePenaltiesArray = new ArrayList<String[]>();
	public static List<Triple<String, String, Integer>> tooNearPenaltiesArray = new ArrayList<Triple<String, String, Integer>>();
	
	static List<Integer> numLinesArray = new ArrayList<Integer>();
	
	/**
	 * Pair
	 * 	Custom pair class to store pairs  
	 *  usage pair.getLeft(), getRight() to get left pair element, etc..
	 * @return nothing
	 */
	public static class Pair<K, V> {

	    private final K left;
	    private final V right;

	    public static  <K, V> Pair<K, V> createPair(K left, V right) {
	        return new Pair<K, V>(left, right);
	    }

	    public Pair(K left, V right) {
	        this.left = left;
	        this.right = right;
	    }

	    // usage pair.getLeft() to get left pair element
	    public K getLeft() {
	        return left;
	    }
	    // usage pair.getRight() to get right pair element
	    public V getRight() {
	        return right;
	    }

	}
	/**
	 * Triple
	 * 	Custom pair class to store triples (for the too-near penalties) 
	 *  usage pair.getTask1(), getTask2() , getPenalty() to get the value 
	 * @return nothing
	 */
	public static class Triple<T1, T2, T3> {
	
	    private final T1 task1;
	    private final T2 task2;
	    private final T3 p;

	    public static  <T1, T2, T3> Triple<T1, T2, T3> createTriple(T1 task1, T2 task2, T3 p) {
	        return new Triple<T1, T2, T3>(task1, task2, p);
	    }

	    public Triple(T1 task1, T2 task2, T3 p) {
	        this.task1 = task1;
	        this.task2 = task2;
	        this.p = p;
	    }

	    // usage pair.getTask1 get to task1
	    public T1 getTask1() {
	        return task1;
	    }
	    public T2 getTask2() {
	        return task2;
	    }
	    public T3 getPenalty() {
	        return p;
	    }

	}
	
	
	/**
	 * @return nothing
	 * reads the input file
	 */
	public static void readInputFile(String inputName) {
		
		//get user to input wanted file for processing
		String inputFile = inputName+".txt";
				
		int lineNum = 1;
		int lineName;
		int linePartial;
		int lineForbidden; 
		int lineTooNear;
		int lineMachPen;	
		int lineTooNearPen; 
		
		//initialize the reader
		BufferedReader br = null;
		String currentLine = "";

		//loads the file into the buffered reader and locate the line number of each contraint.
		try
		{
			br = new BufferedReader(new FileReader(inputFile));
						
			while((currentLine = br.readLine()) != null)
			{
				if(currentLine.equals("Name:"))
				{
					lineName = lineNum;
					numLinesArray.add(lineName);
					//System.out.println(linePartial);
				}	
				
				if(currentLine.equals("forced partial assignment:"))
				{
					linePartial = lineNum;
					numLinesArray.add(linePartial);
					//System.out.println(linePartial);
				}			

				if(currentLine.equals("forbidden machine:"))
				{
					lineForbidden = lineNum;
					numLinesArray.add(lineForbidden);
					//System.out.println(lineForbidden);
				}	
				if(currentLine.equals("too-near tasks:"))
				{
					lineTooNear = lineNum;
					numLinesArray.add(lineTooNear);
					//System.out.println(lineTooNear);
				}	
				if(currentLine.equals("machine penalties:"))
				{
					lineMachPen = lineNum;
					numLinesArray.add(lineMachPen);
					//System.out.println(lineMachPen);
				}	

				if(currentLine.equals("too-near penalties"))
				{
					lineTooNearPen= lineNum;
					numLinesArray.add(lineTooNearPen);
					//System.out.println(lineTooNearPen);
				}	
				
				lineNum++;								
				//System.out.println(currentLine);	//initialize a line to read the file
				
			}	
	
			br.close();	
			
			System.out.println(numLinesArray);
			
			//Check if the formatting is correct by checking numLinesArray
			if (numLinesArray.size() != 6)
				main.displayError();
			else{
				parseInputFile(inputFile);
			}
				
		}

		//Catch read input file exceptions
		catch (FileNotFoundException ex)//if the file isn't found this prints
		{	
			System.out.println("file not found");
			return;
		}
		catch(IOException e)//if there is an IO exception in reading or closing the file this prints
		{
			System.out.println("Error reading file");
			return;
		}
			
		
	}
	

	public static void parseName(int checkLineNum, String currentLine){
	
		if((checkLineNum > numLinesArray.get(0)) && (checkLineNum < numLinesArray.get(1)))
		{
			//System.out.println(currentLine);
		}
	}
	
	/**
	 * parseMachTask
	 * 	Parses each line in the txt file and places them in the Pair class, the Pairs will then be added to each ArrayList
	 * @return nothing
	 */
	public static void parseMachTask(int checkLineNum, String currentLine, List<Pair<Integer, String>> arrayName, int lower, int upper){

	    String regexPair = "(\\()([1-8])(,)([A-H])(\\))"; //pre-defined (mach,task) regular expression
		
		if((checkLineNum > numLinesArray.get(lower)) && (checkLineNum < numLinesArray.get(upper)))
		{
			if (currentLine.matches(regexPair))
			{				
				currentLine = currentLine.replaceAll("[()]", "");
				String[] newCurrent= currentLine.split(", *(?![^\\[\\]]*\\])");

				// create a pair (mach,task)
				Pair<Integer, String> pair = Pair.createPair(Integer.parseInt(newCurrent[0]), newCurrent[1]);
				
				//System.out.println(pair.getRight());
				arrayName.add(pair); // add the pair (mach,task) to array				
			}
			else if (currentLine.equals("")){
				// do nothing, read blank line
			}
			else{
			    //does not match the pre-defined (mach,task) regular expression
				main.displayError();				
			}			
		}
	}
	
	public static void parseTooNearTasks(int checkLineNum, String currentLine){

	    String regexTasks = "(\\()([A-H])(,)([A-H])(\\))"; //pre-defined (task,task) regular expression
	
		if((checkLineNum > numLinesArray.get(3)) && (checkLineNum < numLinesArray.get(4)))
		{
			if (currentLine.matches(regexTasks))
			{				
				currentLine = currentLine.replaceAll("[()]", "");
				String[] newCurrent= currentLine.split(", *(?![^\\[\\]]*\\])");

				// create a pair (mach,task)
				Pair<String, String> pair = Pair.createPair((newCurrent[0]), newCurrent[1]);
				
				tooNeartasksArray.add(pair); // add the pair (mach,task) to array				
			}
			else if (currentLine.equals("")){
				// do nothing, read blank line
			}
			else{
			    //does not match the pre-defined (task,task) regular expression
				main.displayError();				
			}			
		}
	}

	// store the machine penalties in a linear array	
	public static void parseMachinePenalties(int checkLineNum, String currentLine){
	
		//Boolean format = false;
		
		if((checkLineNum > numLinesArray.get(4)) && (checkLineNum < numLinesArray.get(5)))
		{		
			currentLine = currentLine.replace(" ", "");
			
			if (!currentLine.isEmpty()){
				String[] values = currentLine.split("(?!^)");
			
				//System.out.println(values[0]);		
				machinePenaltiesArray.add(values);
			}			
		}
		else if (currentLine.equals("")){
			// do nothing, read blank line
		}			
		else{
			//main.displayError();				
		}		
	}
	
	public static void parseTooNearPenalties(int checkLineNum, String currentLine){
		
		String regexTriple = "(\\()([A-H])(,)([A-H])(,)([1-9][0-9]*)(\\))"; //pre-defined (task,task, penalty) regular expression
			
		if((checkLineNum > numLinesArray.get(5)))
		{
			if (currentLine.matches(regexTriple))
			{
				currentLine = currentLine.replaceAll("[()]", "");
				String[] newCurrent= currentLine.split(", *(?![^\\[\\]]*\\])");

				// create a pair (mach,task)
				Triple<String, String, Integer> triple = Triple.createTriple((newCurrent[0]), newCurrent[1], Integer.parseInt(newCurrent[2]));
				
				tooNearPenaltiesArray.add(triple); // add the triple (task,task,penalty) to array				
			}
			
			else if (currentLine.equals("")){
				// do nothing, read blank line
			}
			else{
				//does not match the pre-defined (task,task,penalty) regular expression
				main.displayError();				
			}			
		}
	}
		
		
	/*
	 *loads the file into the buffered reader again to divide each contraint and store them into different arrays.
	 * 
	 */
	 
	public static void parseInputFile(String inputData){
		
		BufferedReader br = null;
		String currentLine = "";
		int checkLineNum = 1;	
		//int lineNum = 1;
		
		try
		{
			br = new BufferedReader(new FileReader(inputData));
			
			while((currentLine = br.readLine()) != null)
			{				
				// parses each section
				parseName(checkLineNum, currentLine);
				parseMachTask(checkLineNum, currentLine, forcedPartialAssignArray, 1, 2);
				parseMachTask(checkLineNum, currentLine, forbiddenMachineArray, 2, 3);
				parseTooNearTasks(checkLineNum, currentLine);
				parseMachinePenalties(checkLineNum, currentLine);
				parseTooNearPenalties(checkLineNum, currentLine);
					
				checkLineNum++;					
			}
			
			//printing out value of the penalty list at [n][m], [0][0] = 5
			System.out.println(machinePenaltiesArray.get(6)[7]);
			
			/*	//debugging printing out stuff - take a look here to see how to grab the value from each array
			System.out.println(forcedPartialAssignArray.get(0).left);
			System.out.println(forcedPartialAssignArray.get(0).right);
			System.out.println(forcedPartialAssignArray.get(1).left);
			System.out.println(forcedPartialAssignArray.get(1).right);
			System.out.println(forbiddenMachineArray.get(0).left);
			System.out.println(forbiddenMachineArray.get(0).right);
		
			//loops through the tooNearPenaltiesArray and prints out each value at each index
			for (int i = 0; i < tooNearPenaltiesArray.size(); i++)
			{
			System.out.println(tooNearPenaltiesArray.get(i).task1);
			System.out.println(tooNearPenaltiesArray.get(i).task2);
			System.out.println(tooNearPenaltiesArray.get(i).p);
			}
			*/			
			br.close();	
		}
					
		//Catch read input file exceptions
		catch (FileNotFoundException ex)//if the file isn't found this prints
			{	
				System.out.println("file not found");
				return;
			}
			catch(IOException e)//if there is an IO exception in reading or closing the file this prints
			{
				System.out.println("Error reading file");
				return;
			}				
	}

}

