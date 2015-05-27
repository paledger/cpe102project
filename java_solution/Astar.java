//A* Algorithm in Java 

import java.util.LinkedList;
import java.util.List;
import java.lang.Math.*;
import java.util.Collections;
import java.util.Comparator;

public class Astar
{
	private List<Node<Id>> closedSet = new LinkedList<Node<Id>>();
	private List<Node<Id>> openSet = new LinkedList<Node<Id>>();
	
	private int gScore;
	private int fScore;
    private int width;
    private int height;
	
	private Grid grid;
	
	private Point start;
	private Point goal;

	private WorldModel world;

	public Astar(Point start, Point goal, WorldModel world)
	{
		this.start = start;
		//this.openSet.add(startNode);
		this.grid = new Grid(width, height);
		this.goal = goal;
		this.world = world;
		this.width = world.getNumRows();
		this.height = world.getNumCols();
	}

	public LinkedList<Point> Ass() 
	{ // *Ass stands for A Star Search*
		Node<Id> startNode = new Node<Id>(new Id(start, 0, 0), null);
		this.openSet.add(startNode);
		while(openSet.size()!=0)
		{
			orderNodes(openSet);
			Node<Id> current = openSet.get(0);
			Point currentPt = current.getId().getPt();
			if(currentPt == goal)
			{
				LinkedList<Point> path = new LinkedList<Point>();
				return reconstructPath(current, path);
			}
			
			openSet.remove(current);
			closedSet.add(current);
			
			LinkedList<Node<Id>> neighbors = this.findNeighbors(current);
			for(Node<Id> neighbor:neighbors)
			{
				for(int i =0; i < closedSet.size(); i ++)
				{
					if(closedSet.get(i) == neighbor)
					{
						continue;
					}
				}
				
				Id neighborID = neighbor.getId();
				Point neighborPt = neighborID.getPt();
				
				int tentGScore = current.getId().getG() + hScoreFunc(current, neighborPt);
				
				for(int i = 0; i < openSet.size(); i ++)
				{
					if((neighbor != openSet.get(i)) || (tentGScore < (neighborID.getG())))
					{
						neighbor.setFrom(current);
						neighborID.setG(tentGScore);
						neighborID.setF(fScoreFunc(tentGScore, hScoreFunc(neighbor, goal)));
						if(neighbor != openSet.get(i))
						{
							openSet.add(neighbor);
						}
					}	
				}
			}
		}
		
		return new LinkedList<Point>(); // empty list // Failure
	}		
			
	public LinkedList<Node<Id>> findNeighbors(Node<Id> current)
	{
		LinkedList<Node<Id>> output = new LinkedList<Node<Id>>();
		Point currentPt = (current.getId()).getPt();

		Point top = new Point(currentPt.x(), currentPt.y()-1);
		if(top.x() > 0 && top.x() < width-1 &&
			top.y() > 0 && top.y() < height-1 &&
			!this.seeObstacles(top))
		{
			Node<Id> topNode = grid.getCell(top); 
			int htop = hScoreFunc(topNode, goal);
			int ftop = fScoreFunc(gScore, htop);
			Id topId = new Id(top, ftop, htop);
			topNode.setId(topId);
			output.add(topNode);
		}
		
		Point left = new Point(currentPt.x()-1, currentPt.y());
		if(left.x() > 0 && left.x() < width-1 &&
			left.y() > 0 && left.y() < height-1 &&
			!this.seeObstacles(left))
		{
			Node<Id> leftNode = grid.getCell(left); 
			int hleft = hScoreFunc(leftNode, goal);
			int fleft = fScoreFunc(gScore, hleft);
			Id leftId = new Id(left, fleft, hleft);
			leftNode.setId(leftId);
			output.add(leftNode);
		}

		
		Point bottom = new Point(currentPt.x(), currentPt.y()+1);
		if(bottom.x() > 0 && bottom.x() < width-1 &&
			bottom.y() > 0 && bottom.y() < height-1 &&
			!this.seeObstacles(bottom))
		{
			Node<Id> botNode = grid.getCell(bottom); 
			int hBot = hScoreFunc(botNode, goal);
			int fBot = fScoreFunc(gScore, hBot);
			Id botId = new Id(top, fBot, hBot);
			botNode.setId(botId);
			output.add(botNode);
		}

		
		Point right = new Point(currentPt.x()+1, currentPt.y());
		if(right.x() > 0 && right.x() < width-1 &&
			right.y() > 0 && right.y() < height-1 &&
			!this.seeObstacles(right)) 
		{
			Node<Id> rightNode = grid.getCell(right); 
			int hR = hScoreFunc(rightNode, goal);
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

	
	public List<Node<Id>> orderNodes(List<Node<Id>> list)
	{
		Comparator<Node<Id>> comp = new FScoreCompare();
	    Collections.sort(list, comp);
		return list;
	}
		
	public int hScoreFunc(Node<Id> nextOption, Point goal)
	{
		Point nextPt = (nextOption.getId()).getPt();
		int output = Math.abs(nextPt.x()-goal.x()) + Math.abs(nextPt.y()-goal.y());
		return output;
	}
	
	public int fScoreFunc(int overallG, int hScore)
	{
		return overallG+hScore;
	}
	
	public LinkedList<Point> reconstructPath(Node<Id> current, LinkedList<Point> path)
	{
		Point pt = current.getId().getPt();
		path.add(0,pt);
		if(current.getFrom() != null)
		{
			reconstructPath(current.getFrom(), path);
		}
		return path;
	}

}