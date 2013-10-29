public class Calcs
{

	public static int CalculateCost(Assignment[] assignments)
	{
		if(assignments == null)
		{
			return -1;
		}

		// Convert the assignment strings (A,B,C,...,H) to an integer value (0,1,2,...,8)
		// and store in cols[]
		int cols[] = new int[8];
		for(int i = 0; i < assignments.length; i++)
		{
			cols[i] = ((int) assignments[i].mTask.charAt(0))-65;
		}

/* DEBUGGING
System.out.print("cols = [");
for(int i = 0; i < assignments.length; i++)
{
	System.out.print(cols[i]);
}
System.out.print("]\n");
*/
		// Lookup and sum the costs at io.machinePenaltiesArray.get(i)[cols[i]]
		int cost = 0;
		for(int i = 0; i < assignments.length; i++)
		{
			cost += io.machinePenaltiesArray.get(i)[cols[i]];

		}
//System.out.println("machine cost = " + cost);
		return cost;
	}
	
	public static int CalculateFinalCost(Assignment[] assignments)
	{
		if(assignments == null)
		{
			return -1;
		}

		int fcost = 0;
		// For each task proposed assignment, check to see if it occurs in tooNearPenaltiesArray
		for(int i = 0; i < assignments.length; i++)
		{
			for(int j = 0; j < io.tooNearPenaltiesArray.size(); j++)
			{
				if((assignments[i].mTask).equals(io.tooNearPenaltiesArray.get(j).getTask1()))
				{
		//System.out.println("FIRST PENALTY FOUND: " + io.tooNearPenaltiesArray.get(j).getTask1());
					// If it does, check to see if the second task in tooNearPenaltiesArray matches the
					// next value in assignments[]
					if((assignments[(i+1) % (assignments.length)].mTask).equals(io.tooNearPenaltiesArray.get(j).getTask2()))
					{
		//System.out.println("SECOND PENALTY FOUND: " + io.tooNearPenaltiesArray.get(j).getTask2());
						fcost = fcost +  io.tooNearPenaltiesArray.get(j).getPenalty();
		//System.out.println("cost = " + cost);
					}
				}
			}
		}
		//System.out.println("too-near cost = " + cost);

		// Add in the basic machine penalties
		fcost = fcost + CalculateCost(assignments);
		//System.out.println("total cost = " + fcost);

		return fcost;
	}
}
