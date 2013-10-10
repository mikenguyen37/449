/**
 * CPSC449 Fall 2013 
 * @author team 3-3
 * 8 Oct 2013
 * used this http://stackoverflow.com/questions/5287538/how-to-get-basic-user-input-for-java
 */

import java.util.Scanner;

public class main 
{	
	static String inputName;
	
	public static void main(String [] args)
	{
		//String outputName = "output";
		inputName = "input";
		System.out.println("Hello and welcome to team 3-3's 449 a1, java version!" + '\n' );
		
		
		//run initial io class to obtain data for processing, stores data 
		
		String[] userInputs=new String[3];
        Scanner sc=new Scanner(System.in);
        System.out.println("Our system should read their input from file and also write to a file.\n So, if the system is called mysystem,\n the input file is myinput and\n the file for the output is myoutput,\n the command for running your system should be:\n mysystem myinput myoutput");
        System.out.println("=========================");
        System.out.println("Our system is called myProgram");
        System.out.println("Please enter the format as given above:");
        for(int j=0;j<3;j++){
            userInputs[j]=sc.next();
        }
         
        if (userInputs[0].equals("myProgram")){
        	io.readInputFile(userInputs[1]);
        	//fileOutput.makeOutputFile(userInputs[2]);
        }
        else{
        	//System.out.println(userInputs[0]);
        	System.out.println("Sorry, the system you typed: "+userInputs[0]+" is not recognised.");
        }	

	}
	
	public static void displayError(){
		System.out.println("Error while parsing input file..." + "\n");
		io.readInputFile(inputName);
	}
	
}