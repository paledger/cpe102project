//A* Algorithm in Java 

import java.util.LinkedList;
import java.util.List;

public class Astar
{
	private List<T> closedSet = new LinkedList();
	private List<T> openSet = new LinkedList();
	private T[][] cameFrom;

	private double gScore;
	private double fScore;

	public Astar(T start, T goal)
	{
		this.openSet.add(start);
		
	}
}