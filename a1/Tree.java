import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * CPSC449 Fall 2013 
 * @author team 3-3
 * 8 Oct 2013
 */

public class Tree
{
	private static final String[] TASKS = {"A", "B", "C", "D", "E", "F", "G", "H"};
	private static final int LENGTH = 8;
	
	// this function recurses and builds a tree
	public Assignment[] CalculateBestSolution(Node node, int depth)
	{
		// check nodes that have been fixed to see if they break any hard restrictions
		Assignment[] setNodes = new Assignment[depth];
		
		for (int i = 0; i < setNodes.length; i++)
		{
			setNodes[i] = node.mAssignnments[i];
		}
		
		// if the nodes that have already been set break a hard restriction return null
		if (!IsValid(setNodes))
		{
			return null;
		}
		// when the node is a leaf return
		if (depth == LENGTH)
		{
			return node.mAssignnments;
		}
		// if solution[depth] is a forced partial assignment then snip all other branches and recurse
		if (node.mAssignnments[depth].mForced)
		{
			return CalculateBestSolution(node, depth + 1);
		}
		
		node.mChildren = new Node[LENGTH];
		
		for (int i = 0; i < LENGTH; i++)
		{
			node.mChildren[i] = new Node();
			// fill out each childs assignments
			node.mChildren[i].mAssignnments = FillSolution(node.mAssignnments, depth, TASKS[i]);
			node.mChildren[i].mCost = Calcs.CalculateCost(node.mChildren[i].mAssignnments);
		}
		// order child nodes by cost in ascending order
		OrderChildNodesByCost(node);
		
		
		int bestCost = -1;
		Assignment[] bestSolution = null;
		
		for (int i = 0; i < node.mChildren.length; i++)
		{
			// if the current childs temp cost < best cost we must check it
			// or if the best solution has not been set check this child for the best solution
			if (node.mChildren[i].mCost < bestCost || bestSolution == null)
			{
				Assignment[] possibleSolution = CalculateBestSolution(node.mChildren[i], depth + 1);
				
				if (possibleSolution == null || !IsValid(possibleSolution))
				{
					continue;
				}
				
				int possibleCost = Calcs.CalculateFinalCost(possibleSolution);
				
				if (possibleCost < bestCost || bestSolution == null)
				{
					bestSolution = possibleSolution;
					bestCost = possibleCost;
				}
			}
			// if the best cost is lower than a temp cost we may return as they are sorted in asceding temp costs thus there are no lower costs
			else
			{
				break;
			}
		}

		return bestSolution;
	}
	
	Assignment[] FillSolution(Assignment[] fillFrom, int depth, String taskAtDepth)
	{
		Assignment[] solution = new Assignment[LENGTH];
		
		for (int i = 0; i < depth; i++)
		{
			solution[i] = fillFrom[i];
		}
		
		solution[depth] = new Assignment();
		solution[depth].mTask = taskAtDepth;
		
		for (int i =  depth + 1; i < LENGTH; i++)
		{
			solution[i] = fillFrom[i];
		}
		
		return solution;
	}
	
	boolean IsValid(Assignment[] solution)
	{
		// should be able to accept an array of any length and simply loop through 
		// and check for broken hard restrictions slash duplicated machines or tasks
		// todo check if a solution is valid need IO
		
		ArrayList<String> dupes = new ArrayList<String>();
		
		for (int i = 0; i < solution.length; i++)
		{
			if (dupes.contains(solution[i].mTask))
			{
				return false;
			}
			else
			{
				dupes.add(solution[i].mTask);
			}
			
			if (io.CheckForbidden(i + 1, solution[i].mTask))
			{
				return false;
			}
			
			String forcedTask = io.GetForced(i + 1);
			
			if (forcedTask != null && forcedTask != solution[i].mTask)
			{
				return false;
			}
		}
		
		if (io.TooClose(solution))
		{
			return false;
		}
		
		return true;
	}
	
	
	
	
	public void OrderChildNodesByCost(Node node)
	{
		int j;
		Node temp;
		for (int i = 0; i < node.mChildren.length;i++)
		{
			j = i;
			for(int k = i; k < node.mChildren.length;k++)
			{
				if(node.mChildren[j].mCost > node.mChildren[k].mCost)
				{
					j = k;
				}
			}
			temp = node.mChildren[i];
			node.mChildren[i] = node.mChildren[j];
			node.mChildren[j] = temp;
		}
	}
	
/*	public int CalculateCost(Assignment[] assignments)
	{
		if (assignments == null)
		{
			return -1;
		}
		
		// [not done] need io
		return 0;
	}
	
	public int CalculateFinalCost(Assignment[] assignments)
	{
		if (assignments == null)
		{
			return -1;
		}
		
		// [not done] need io
		return 0;
	} */
	
	
	
	
	

}

