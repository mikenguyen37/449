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
	public static List<Integer[] >machinePenaltiesArray = new  ArrayList<Integer[]>();
	public static List<Triple<String, String, Integer>> tooNearPenaltiesArray = new ArrayList<Triple<String, String, Integer>>();


	
	public static boolean CheckForbidden(int machine, String task)
	{
		for (int i = 0; i < forbiddenMachineArray.size(); i++)
		{
			if (forbiddenMachineArray.get(i).getLeft() == machine && forbiddenMachineArray.get(i).getRight().equalsIgnoreCase(task))
			{
				return true;
			}
		}	
		return false;
	}
	
	
	

	
	public static boolean TooClose(Assignment[] assignments )
		{
			if(assignments == null)
			{
				return false;
			}
			if (assignments.length < 2)
			{
				return false;
			}
			for(int i = 0; i < assignments.length; i++)
			{
				for(int j = 0; j < io.tooNeartasksArray.size(); j++)
				{
					if((assignments[i].mTask).equals(io.tooNeartasksArray.get(j).getLeft()))
					{

						if((assignments[(i+1) % (assignments.length)].mTask).equals(io.tooNeartasksArray.get(j).getRight()))
						{

							return true;
						}
					}
				}
			}	
			
			/*for(int i = 0; i <= assignments.length; i++){
				for(int j = i+1; j < assignments.length; j++){
					if(assignments[i].mTask.equals(assignments[j].mTask))
						return true;
				}	
			}*/
			return false;
		}
	
	
	
	public static String GetForced(int machine)
	{
		for (int i = 0; i < forcedPartialAssignArray.size(); i++)
		{
			if (forcedPartialAssignArray.get(i).getLeft() == machine)
			{
				return  forcedPartialAssignArray.get(i).getRight();
			}
		}
		
		return null;
	}
	
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

			br = new BufferedReader(new FileReader(inputName));
						
			while((currentLine = br.readLine()) != null)
			{
//System.out.println("numLinesArray = " + numLinesArray.size());
				if(currentLine.equals("Name:"))
				{
					lineName = lineNum;
					numLinesArray.add(lineName);
//System.out.println("Test 1");

				}	
				
				if(currentLine.equals("forced partial assignment:"))
				{
					linePartial = lineNum;
					numLinesArray.add(linePartial);
//System.out.println("Test 2");

				}			

				if(currentLine.equals("forbidden machine:"))
				{
					lineForbidden = lineNum;
					numLinesArray.add(lineForbidden);
//System.out.println("Test 3");

				}	
				if(currentLine.equals("too-near tasks:"))
				{
					lineTooNear = lineNum;
					numLinesArray.add(lineTooNear);
//System.out.println("Test 4");

				}	
				if(currentLine.equals("machine penalties:"))
				{
					lineMachPen = lineNum;
					numLinesArray.add(lineMachPen);
//System.out.println("Test 5");

				}	

				if(currentLine.equals("too-near penalities"))
				{
					lineTooNearPen= lineNum;
					numLinesArray.add(lineTooNearPen);
//System.out.println("Test 6");

				}	

				lineNum++;								
//System.out.println(currentLine);	//initialize a line to read the file

			}

			br.close();
//System.out.println(numLinesArray.size());
			//Check if the formatting is correct by checking numLinesArray
			if (numLinesArray.size() != 6)
				main.displayError(1);
			else{
				parseInputFile(inputName);
			}
//System.out.println("TESTEST2");		
		}

		//Catch read input file exceptions
		catch (FileNotFoundException ex)//if the file isn't found this prints
		{	
			System.out.println("file not found");
			System.exit(0);
			return;
		}
		catch(IOException e)//if there is an IO exception in reading or closing the file this prints
		{
			System.out.println("Error reading file");
			return;
		}
	





















                //re-initialize the reader

                 currentLine = "";

                try
                {
                        br = new BufferedReader(new FileReader(inputName));

                        for(int i = 0; i < numLinesArray.get(0)-1; i++)
                        {
                                currentLine = br.readLine();
                                currentLine = currentLine.replaceAll(" ", "");
                                if(currentLine.equals(""))
                                {
                                        //read a blank line, do nothing
                                }
                                else{
//System.out.println(i);
//System.out.println(numLinesArray.get(0));
                                        main.displayError(1);
				}
                        }

                        br.close();
                
                }

                //Catch read input file exceptions
                catch (FileNotFoundException ex)//if the file isn't found this prints
                {        
                        System.out.println("file not found");
                        System.exit(0);
                        return;
                }
                catch(IOException e)//if there is an IO exception in reading or closing the file

                {
                        System.out.println("Error reading file");
                        return;
                }
                        
                
     





                //re-initialize the reader

                 currentLine = "";

                try
                {

                        br = new BufferedReader(new FileReader(inputName));
                        int countLines = 0; //counter of non-blank lines
                        for(int i = 0; i < numLinesArray.get(1)-1; i++)
                        {
 
                                currentLine = br.readLine();
                                currentLine = currentLine.replaceAll(" ", "");
                                if(currentLine.equals(""))
                                {
                                        //read a blank line, do nothing
                                }
                                else
                                        countLines++;

                        }

                        if(countLines > 2)
                                main.displayError(1);

                        br.close();
                
                }
                catch (FileNotFoundException ex)//if the file isn't found this prints
                {        
                        System.out.println("file not found");
                        System.exit(0);
                        return;
                }
                catch(IOException e)//if there is an IO exception in reading or closing the file

                {
                        System.out.println("Error reading file");
                        return;
                }






                //re-initialize the reader

                 currentLine = "";

                try
                {

                        br = new BufferedReader(new FileReader(inputName));
                        int countLines = 0; //counter of non-blank lines
                        for(int i = numLinesArray.get(4); i < numLinesArray.get(5)-1; i++)
                        {
 
                                currentLine = br.readLine();
                                currentLine = currentLine.replaceAll(" ", "");
                                if(currentLine.equals(""))
                                {
                                        //read a blank line, do nothing
                                }
                                else
                                        countLines++;

                        }

                        if(countLines > 8)
                                main.displayError(1);

                        br.close();
                
                }
                catch (FileNotFoundException ex)//if the file isn't found this prints
                {        
                        System.out.println("file not found");
                        System.exit(0);
                        return;
                }
                catch(IOException e)//if there is an IO exception in reading or closing the file

                {
                        System.out.println("Error reading file");
                        return;
                }
















		
		
	}
	
	public static void parseName(int checkLineNum, String currentLine){
	
		if((checkLineNum > numLinesArray.get(0)) && (checkLineNum < numLinesArray.get(1)))
		{

		}
	}
	
	/**
	 * parseMachTask
	 * 	Parses each line in the txt file and places them in the Pair class, the Pairs will then be added to each ArrayList
	 * @return nothing
	 */
	public static void parseMachTask(int checkLineNum, String currentLine, List<Pair<Integer, String>> arrayName, int lower, int upper)
	{


	    String regexPair = "(\\()([1-8])(,)([A-H])(\\))"; //pre-defined (mach,task) regular expression
		
		if((checkLineNum > numLinesArray.get(lower)) && (checkLineNum < numLinesArray.get(upper)))
		{
//System.out.println("TESTEST1");
//System.out.println("currentLine = " + currentLine + " regexPair = " + regexPair);
			if (currentLine.matches(regexPair))
			{	
//System.out.println("TESTEST2");			
				currentLine = currentLine.replaceAll("[()]", "");
				currentLine = currentLine.replaceAll(" ", "");
				String[] newCurrent= currentLine.split(", *(?![^\\[\\]]*\\])");

				// create a pair (mach,task)
				Pair<Integer, String> pair = Pair.createPair(Integer.parseInt(newCurrent[0]), newCurrent[1]);
				
				//System.out.println(pair.getRight());
				arrayName.add(pair); // add the pair (mach,task) to array				
			}
			else if (currentLine.equals("")){
//System.out.println("TESTEST3");
				// do nothing, read blank line
			}
			else if (currentLine.equals(" ")){
//System.out.println("TESTEST3b");
				// do nothing, read blank line
			}
			else{
//System.out.println("TESTEST4");
			    //does not match the pre-defined (mach,task) regular expression
				if (currentLine.charAt(0) != '(')
			    	{
					main.displayError(1);
			    	}
				main.displayError(3);	//machine penalty error 			
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
				currentLine = currentLine.replaceAll(" ", "");
				String[] newCurrent= currentLine.split(", *(?![^\\[\\]]*\\])");

				// create a pair (mach,task)
				Pair<String, String> pair = Pair.createPair((newCurrent[0]), newCurrent[1]);
				
				tooNeartasksArray.add(pair); // add the pair (mach,task) to array				
			}
			else if (currentLine.equals("")){
				// do nothing, read blank line
			}
			else{
				if (currentLine.charAt(0) != '(')
			    	{
					main.displayError(1);
			    	}
			    //does not match the pre-defined (task,task) regular expression
				main.displayError(3); //too near error				
			}			
		}
	}

	// store the machine penalties in a linear array	
	public static void parseMachinePenalties(int checkLineNum, String currentLine)
	{
	
		//Boolean format = false;
		
		if((checkLineNum > numLinesArray.get(4)) && (checkLineNum < numLinesArray.get(5)))
		{		
			currentLine = currentLine.replace(" ", "-");
			
			if (!currentLine.isEmpty()){
				String[] split = currentLine.split("-");
				Integer[] values = new Integer [split.length];
				




















				for (int i = 0; i < split.length; i++)
				{
					try
					{
						values[i] = Integer.parseInt(split[i]);
					}
					catch (NumberFormatException e)
					{
						main.displayError(5); //check for errors
					}
				}
				machinePenaltiesArray.add(values);

			}			
		}
		else if (currentLine.equals("")){
			// do nothing, read blank line
		}			
		else{
			//main.displayError(2);	
		}		
	}
	
	public static void parseTooNearPenalties(int checkLineNum, String currentLine){
		
		String regexTriple = "(\\()([A-H])(,)([A-H])(,)([1-9][0-9]*)(\\))"; //pre-defined (task,task, penalty) regular expression
		String regex2 = "(\\()([A-H])(,)([A-H])(,)([A-Z])(\\))";



		if((checkLineNum > numLinesArray.get(5)))
		{
			if (currentLine.matches(regexTriple))
			{
				currentLine = currentLine.replaceAll("[()]", "");
				currentLine = currentLine.replaceAll(" ", "");
				String[] newCurrent= currentLine.split(", *(?![^\\[\\]]*\\])");

				// create a pair (mach,task)
				Triple<String, String, Integer> triple = Triple.createTriple((newCurrent[0]), newCurrent[1], Integer.parseInt(newCurrent[2]));
				
				tooNearPenaltiesArray.add(triple); // add the triple (task,task,penalty) to array				
			}
			else if (currentLine.equals(""))
			{
				// do nothing, read blank line
			}
			else if (currentLine.matches(regex2))
			{
				main.displayError(5);
			}
			else
			{
				if (currentLine.charAt(0) != '(')
			    	{
					main.displayError(1);
			    	}
				main.displayError(4);
			}
		}
	}
		
	public static Boolean hasPartialDuplicates(){

	List<Pair<Integer, String>> forcedPartialDuplicates = new ArrayList<Pair<Integer, String>>();

	for (int i = 0; i < forcedPartialAssignArray.size(); i++)
	{
		for(int j = i+1; j< forcedPartialAssignArray.size();j++)
		{
			if( forcedPartialAssignArray.get(i).getLeft() == forcedPartialAssignArray.get(j).getLeft())
			{
				//System.out.println("there was a dupe MACH");
				return true;
			}
			if( forcedPartialAssignArray.get(i).getRight().equals(forcedPartialAssignArray.get(j).getRight()))
			{
				//System.out.println("there was a dupe TASK");
				return true;
			}
		}
	}
	return false;
	}
	
		
	/*
	 *loads the file into the buffered reader again to divide each contraint and store them into different arrays.
	 * 
	 */
	 
	public static void parseInputFile(String inputData)
	{
		
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
//System.out.println("TK PARSENAME");
					
				parseName(checkLineNum, currentLine);

//System.out.println("TK PARSEMACHTASK");
				parseMachTask(checkLineNum, currentLine, forcedPartialAssignArray, 1, 2);
//System.out.println("TK PARSEMACHTASK 2");
//System.out.println(forbiddenMachineArray.size());
				parseMachTask(checkLineNum, currentLine, forbiddenMachineArray, 2, 3);
//System.out.println("TK PARSETOONEARTASKS");
				parseTooNearTasks(checkLineNum, currentLine);
//System.out.println("TK PARSEMACHINEPENALTIES");
				parseMachinePenalties(checkLineNum, currentLine);
//System.out.println("TK PARSEMACHINEPENALTIES 2");
				parseTooNearPenalties(checkLineNum, currentLine);
					
				checkLineNum++;					
			}
			
			// check for some errors
			if (hasPartialDuplicates() == true)
			{
				main.calltoMakeOutputFile("partial assignment error");
				//System.out.println("TEST1");
				System.exit(0);
			}

			if (machinePenaltiesArray.size() != 8)
			{
				//System.out.println("TEST2");
				main.displayError(2);
			}
			
			for (int i = 0; i < machinePenaltiesArray.size(); i++)
			{
				if (machinePenaltiesArray.get(i).length != 8)
				{
				//System.out.println("TEST3" + machinePenaltiesArray.get(i).length);
					main.displayError(2);
				}
			}

			// end check
			br.close();

			//printing out value of the penalty list at [n][m], [0][0] = 5
			//System.out.println(machinePenaltiesArray.get(6)[7]);
			
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

