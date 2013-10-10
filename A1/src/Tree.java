/**
 * CPSC449 Fall 2013 
 * @author team 3-3
 * 8 Oct 2013
 */

public class Tree
{
	private static final String[] TASKS = {"A", "B", "C", "D", "E", "F", "G", "H"};
	private static final int LENGTH = 8;
	
	Node mRootNode = new Node();
	
	public Assignment[] CalculateBestSolution(Node node, int depth)
	{
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
		
		node.mChildren = new Node[LENGTH];
		
		for (int i = 0; i < LENGTH; i++)
		{
			node.mChildren[i].mAssignnments = FillSolution(node.mAssignnments, depth, TASKS[i]);
			node.mChildren[i].mCost = CalculateCost(node.mChildren[i].mAssignnments);
		}
		OrderChildNodesByCost(node);
		
		Assignment[] bestSolution = null;
		int bestCost = -1;
		
		for (int i = 0; i < node.mChildren.length; i++)
		{
			if (node.mChildren[i].mCost < bestCost || bestSolution == null)
			{
				Assignment[] possibleSolution = CalculateBestSolution(node.mChildren[i], depth + 1);
				
				if (!IsValid(possibleSolution))
				{
					continue;
				}
				
				int possibleCost = CalculateFinalCost(node.mChildren[i].mAssignnments);
				
				if (possibleCost < bestCost || bestSolution == null)
				{
					bestSolution = possibleSolution;
					bestCost = possibleCost;
				}
			}
			else
			{
				break;
			}
		}
		return bestSolution;
	}
	
	Assignment[] FillSolution(Assignment[] fillFrom, int depth, String taskAtDepth)
	{
		// todo construct child node assignments here
		// depth will be the first not set index so for example
		// at depth 5 the last node would have been {a, b, c, d, e, x, x, x}
		
		Assignment[] solution = new Assignment[LENGTH];
		
		for (int i = 0; i < depth; i++)
		{
			solution[i] = fillFrom[i];
		}
		
		solution[depth] = new Assignment();
		solution[depth].mTask = taskAtDepth;
		
		for (int i =  depth + 1; i < LENGTH; i++)
		{
			// do stuff
		}
		
		return solution;
	}
	
	boolean IsValid(Assignment[] solution)
	{
		// should be able to accept an array of any length and simply loop through 
		// and check for broken hard restrictions slash duplicated machines or tasks
		// todo check if a solution is valid need IO
		return false;
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
	
	public int CalculateCost(Assignment[] assignments)
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
	}
}

