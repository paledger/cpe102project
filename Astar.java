//A* Algorithm in Java 

import java.util.LinkedList;
import java.util.List;
import java.lang.Math.*;
import java.util.Collections;
import java.util.Comparator;
import java.lang.UnsupportedOperationException;

public class Astar
{
	private List<Node<Id>> closedSet = new LinkedList<Node<Id>>();
	private List<Node<Id>> openSet = new LinkedList<Node<Id>>();
	
	private int gScore;
	private int fScore;
   private int width;
   private int height;
	
	private Point start;
	private Point goal;

	private WorldModel world;

	public Astar(Point start, Point goal, WorldModel world)
	{
		this.start = start;

		//this.openSet.add(startNode);
		this.width = world.getNumRows();
		this.height = world.getNumCols();
		this.goal = goal;
		this.world = world;
	}

	public LinkedList<Point> Ass() 
	{ // *Ass stands for A Star Search*
		// perhaps later add check for if goal is an obstacle
		world.fillNodes(goal);
		//fSystem.out.print(world.getNodes()
		closedSet.clear();
		openSet.clear();

		Node<Id> startNode = new Node<Id>(new Id(start, 0, fScoreFunc(0, hScoreFunc(start, goal))), null);
		startNode.getId().setG(0);
		openSet.add(startNode);
		closedSet.add(startNode);
		openSet = orderNodes(openSet);
	
		Node<Id> current = openSet.get(0);
		Point currentPt = current.getId().getPt();

		while(!openSet.isEmpty())//openSet.size() != 0)
		{
			System.out.println(start);
			System.out.println(goal);
			//System.out.println(openSet.size());
			openSet = orderNodes(openSet);

			current = openSet.get(0);
			openSet.remove(0);
			currentPt = current.getId().getPt();
			System.out.println(currentPt);
			
			//prints: 1: is it the goal, 2: what is the size of openset, 3: how many neighbors are there
			System.out.println(currentPt.x() == goal.x() && currentPt.y() == goal.y());
			System.out.println(openSet.size());
			if(currentPt.x() == goal.x() && currentPt.y() == goal.y())
			{
				System.out.println("reached loop");
				 
				return reconstructPath(current);
				//it's not returning reconstruct path
			}

		   openSet.remove(current);
			closedSet.add(current);

			LinkedList<Node<Id>> neighbors = findNeighbors(current);
			//System.out.println(neighbors.size());
			for(Node<Id> neighbor:neighbors)
			{
				boolean option;
				if(closedSet.contains(neighbor))
				{
					continue;
				}
				int tentG = (current.getId().getG()+1);
				if(tentG < neighbor.getId().getG())
				{
					openSet.remove(neighbor);
					closedSet.remove(neighbor);
				}
				if(!(openSet.contains(neighbor)&&!closedSet.contains(neighbor)))
				{
					neighbor.getId().setG(tentG);
					neighbor.getId().setF(neighbor.getId().getG()+hScoreFunc(neighbor.getId().getPt(),goal));
					neighbor.setFrom(current);
					openSet.add(neighbor);
				}
			
				/*if(closedSet.contains(neighbor))
				{
					continue;
				}

				int tentGScore = current.getId().getG() + hScoreFunc(currentPt, neighbor.getId().getPt());
				Id neighborID = neighbor.getId();
				Point neighborPt = neighborID.getPt();

				if(!openSet.contains(neighbor) || tentGScore < neighborID.getG())
				{
					neighbor.setFrom(current);
					neighborID.setG(tentGScore);
					neighborID.setF(fScoreFunc(neighbor.getId().getG(), hScoreFunc(neighbor.getId().getPt(), goal)));

					if (!openSet.contains(neighbor))
					{
						openSet.add(neighbor);
						orderNodes(openSet);
					}
					if (!closedSet.contains(current))
					{
						closedSet.add(current);
					}
				}*/
			}
		}
		return new LinkedList<Point>(); // empty list // Failure
	}	
	
			
	public LinkedList<Node<Id>> findNeighbors(Node<Id> current)
	{
		LinkedList<Node<Id>> temp = new LinkedList<Node<Id>>();
		LinkedList<Node<Id>> output = new LinkedList<Node<Id>>();
		Point currentPt = (current.getId()).getPt();
		int currX = currentPt.x();
		int currY = currentPt.y();

		Point top = new Point(currentPt.x(), currentPt.y()-1);
		if(world.withinBounds(top) &&
			!this.seeObstacles(top))
		{
			Node<Id> topNode = world.getNode(top);
			int htop = hScoreFunc(top, goal);
			int ftop = fScoreFunc(gScore, htop);
			Id topId = new Id(top, ftop, htop);
			topNode.setId(topId);
			output.add(topNode);
		}
		
		Point left = new Point(currentPt.x()-1, currentPt.y());
		if(world.withinBounds(left) &&
			!this.seeObstacles(left))
		{
			Node<Id> leftNode = world.getNode(left); 
			int hleft = hScoreFunc(left, goal);
			int fleft = fScoreFunc(gScore, hleft);
			Id leftId = new Id(left, fleft, hleft);
			leftNode.setId(leftId);
			output.add(leftNode);
		}

		
		Point bottom = new Point(currentPt.x(), currentPt.y()+1);
		if(world.withinBounds(bottom) &&
			!this.seeObstacles(bottom))
		{
			Node<Id> botNode = world.getNode(bottom); 
			int hBot = hScoreFunc(bottom, goal);
			int fBot = fScoreFunc(gScore, hBot);
			Id botId = new Id(top, fBot, hBot);
			botNode.setId(botId);
			output.add(botNode);
		}

		
		Point right = new Point(currentPt.x()+1, currentPt.y());
		if(world.withinBounds(right) &&
			!this.seeObstacles(right)) 
		{
			Node<Id> rightNode = world.getNode(right); 
			int hR = hScoreFunc(right, goal);
			int fR = fScoreFunc(gScore, hR);
			Id rightId = new Id(top, fR, hR);
			rightNode.setId(rightId);
			output.add(rightNode);
		}
		
		return output;
	}

	public boolean seeObstacles(Point current)
	{
		return this.world.isOccupied(current);
	}

	
	public static List<Node<Id>> orderNodes(List<Node<Id>> list)
	{
		Comparator<Node<Id>> comp = new FScoreCompare();
	   Collections.sort(list, comp);
		return list;
	}

	public int gScoreFunc(Node<Id> current, Point start)
	{
		LinkedList<Point> currPath = new LinkedList<Point>();
		return reconstructPath(current).size();
	}
		
	public static int hScoreFunc(Point nextOption, Point goal)
	{
		Point nextPt = nextOption;
		int output = Math.abs(nextPt.x()-goal.x()) + Math.abs(nextPt.y()-goal.y());
		return output;
	}
	
	public int fScoreFunc(int overallG, int hScore)
	{
		return overallG+hScore;
	}
	
	public LinkedList<Point> reconstructPath(Node<Id> current)
	{
		LinkedList<Point> path = new LinkedList<Point>();
		Point pt = current.getId().getPt();
		System.out.print(current.getFrom());
		while(!(current.getFrom()==null))
		{
			System.out.print("inside path while");
			path.add(0, current.getId().getPt());
			current = current.getFrom();
		}
		return path;
		//System.out.print(path);
		/*if(current.getFrom() != null)
		{
			reconstructPath(current.getFrom(), path);
		}*/
		
		//System.out.print(path);
	}

}