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
		this.width = world.getNumRows();
		this.height = world.getNumCols();
		this.grid = new Grid(width, height);
		this.goal = goal;
		this.world = world;
	}

	public LinkedList<Point> Ass() 
	{ // *Ass stands for A Star Search*
		grid.createCells();
		Node<Id> startNode = new Node<Id>(new Id(start, 0, 0+this.hScoreFunc(start,goal)), null);
		this.openSet.add(startNode);
		while(openSet.size()!=0)
		{
			Node<Id> current = orderNodes(openSet).get(0);
			Point currentPt = current.getId().getPt();
			if(currentPt == goal)
			{
				LinkedList<Point> path = new LinkedList<Point>();
				return reconstructPath(current, path);
				//What is reconstructPath
			}
			
			openSet.remove(current);
			closedSet.add(current);
			
			LinkedList<Node<Id>> neighbors = this.findNeighbors(current);
			for(Node<Id> neighbor:neighbors)
			{
				for(int i =0; i < closedSet.size()-1; i ++)
				{
					if(closedSet.get(i) == neighbor)
					{
						continue;
					}
				
					Id neighborID = neighbor.getId();
					Point neighborPt = neighborID.getPt();
				
					int tentGScore = current.getId().getG() + hScoreFunc(current.getId().getPt(), neighborPt);
				
					for(int j = 0; j < openSet.size()-1; j ++)
					{
						if((neighbor != openSet.get(i)) || (tentGScore < (neighborID.getG())))
						{
							neighbor.setFrom(current);
							neighborID.setG(tentGScore);
							neighborID.setF(fScoreFunc(tentGScore, hScoreFunc(neighbor.getId().getPt(), goal)));
							if(neighbor != openSet.get(i))
							{
								openSet.add(neighbor);
							}
						}	
					}
				}
			}
			return this.NodetoPoint(openSet);
		}
		
		return null; // empty list // Failure
	}	
	
	public LinkedList<Point> NodetoPoint(List<Node<Id>> list)
		{
			LinkedList<Point> output = new LinkedList();
			for(Node<Id> node: list)
			{
				output.add(node.getId().getPt());
			}
			return output;
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
			Node<Id> topNode = world.getNode(world.nodes,top);
			int htop = hScoreFunc(top, goal);
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
			Node<Id> leftNode = world.getNode(world.nodes,left); 
			int hleft = hScoreFunc(left, goal);
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
			Node<Id> botNode = world.getNode(world.nodes,bottom); 
			int hBot = hScoreFunc(bottom, goal);
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
			Node<Id> rightNode = world.getNode(world.nodes,right); 
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

	
	public List<Node<Id>> orderNodes(List<Node<Id>> list)
	{
		Comparator<Node<Id>> comp = new FScoreCompare();
	    Collections.sort(list, comp);
		return list;
	}
		
	public int hScoreFunc(Point nextOption, Point goal)
	{
		Point nextPt = (nextOption);
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