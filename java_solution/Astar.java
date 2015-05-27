//A* Algorithm in Java 

import java.util.LinkedList;
import java.util.List;
import java.lang.Math.*;

public class Astar
{
	private List<Node<Id>> closedSet = new LinkedList<Node<Id>>();
	private List<Node<Id>> openSet = new LinkedList<Node<Id>>();
	
	private double gScore;
	private double fScore;
    private int width;
    private int height;
	
	private Grid grid;
	
	private Point goal;

	private WorldModel world;

	public Astar(Point start, Point goal, WorldModel world)
	{
		Node<Id> startNode = new Node<Id>(new Id(start, 0, 0), null);
		this.openSet.add(startNode);
		this.grid = new Grid(width, height);
		this.goal = goal;
		this.world = world;
		this.width = world.getNumRows();
		this.height = world.getNumCols();
	}

	public LinkedList<Point> Ass()
	{
		while(openSet.size()!=0)
		{
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
				
				double tentGScore = current.getId().getG() + hScoreFunc(current, neighborPt);
				
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
		if(top.x() > 0 && top.x() < width &&
			top.y() > 0 && top.y() < height &&
			!this.seeObstacles(top))
		{
			Node<Id> topNode = grid.getCell(top); 
			double htop = hScoreFunc(topNode, goal);
			double ftop = fScoreFunc(gScore, htop);
			Id topId = new Id(top, ftop, htop);
			topNode.setId(topId);
			output.add(topNode);
		}
		
		Point left = new Point(currentPt.x()-1, currentPt.y());
		if(left.x() > 0 && left.x() < width &&
			left.y() > 0 && left.y() < height &&
			!this.seeObstacles(left))
		{
			Node<Id> leftNode = grid.getCell(left); 
			double hleft = hScoreFunc(leftNode, goal);
			double fleft = fScoreFunc(gScore, hleft);
			Id leftId = new Id(left, fleft, hleft);
			leftNode.setId(leftId);
			output.add(leftNode);
		}

		
		Point bottom = new Point(currentPt.x(), currentPt.y()+1);
		if(bottom.x() > 0 && bottom.x() < width &&
			bottom.y() > 0 && bottom.y() < height &&
			!this.seeObstacles(bottom))
		{
			Node<Id> botNode = grid.getCell(bottom); 
			double hBot = hScoreFunc(botNode, goal);
			double fBot = fScoreFunc(gScore, hBot);
			Id botId = new Id(top, fBot, hBot);
			botNode.setId(botId);
			output.add(botNode);
		}

		
		Point right = new Point(currentPt.x()+1, currentPt.y());
		if(right.x() > 0 && right.x() < width &&
			right.y() > 0 && right.y() < height &&
			!this.seeObstacles(right)) 
		{
			Node<Id> rightNode = grid.getCell(right); 
			double hR = hScoreFunc(rightNode, goal);
			double fR = fScoreFunc(gScore, hR);
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

	/*
	public LinkedList<Node<Id>> orderNodes(LinkedList<Node<Id>> list)
	{
		/*
		minNode = list.get(0);
		for(int i = 0; i < list.size(); i++)
		{
			if findMin
		}
	   
		
			//Comparator<
	}
	*/
		
	public Node<Id> findMin(Node<Id> one, Node<Id> two)
	{
		if(one.getId().getF() < two.getId().getF())
		{
			return one;
		}
		else
		{
			return two;
		}
	}
	
	
	public double hScoreFunc(Node<Id> nextOption, Point goal)
	{
		Point nextPt = (nextOption.getId()).getPt();
		double output = Math.abs(nextPt.x()-goal.x()) + Math.abs(nextPt.y()-goal.y());
		return output;
	}
	
	public double fScoreFunc(double overallG, double hScore)
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