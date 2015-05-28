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
	
	private Point start;
	private Point goal;

	private WorldModel world;

	public Astar(Point start, Point goal, WorldModel world)
	{
		this.start = start;
		Node<Id> startNode = new Node<Id>(new Id(start, 0, 0 + this.hScoreFunc(start,goal)), null);

		this.openSet.add(startNode);
		this.width = world.getNumRows();
		this.height = world.getNumCols();
		this.goal = goal;
		this.world = world;
	}

	public LinkedList<Point> Ass() 
	{ // *Ass stands for A Star Search*

		while(openSet.size()!=0)
		{
			Node<Id> current = orderNodes(openSet).get(0);
			Point currentPt = current.getId().getPt();
			if(currentPt == goal)
			{
				LinkedList<Point> path = new LinkedList<Point>();
				return reconstructPath(current, path);
			}

			if (currentPt != start)
			{
			    openSet.remove(current);
				closedSet.add(current);
			}

			LinkedList<Node<Id>> neighbors = this.findNeighbors(current);
			//System.out.print(neighbors); // DEBUGGING
		    outerloop:
			for(Node<Id> neighbor:neighbors)
			{
				//System.out.print("a neighbor has been found."); // DEBUGGING  NOTHING IN CLOSEDSET THAT'S WHY IT DOESN'T RUN
				for(int i = 0; i < closedSet.size(); i ++)
				{
					System.out.print(" in closedSet loop. ");
					Point cPt = closedSet.get(i).getId().getPt();
					Point nPt = neighbor.getId().getPt();
					if(cPt.x() == nPt.x() && cPt.y() == nPt.y())
					{
						System.out.print("neighbor is in closedSet"); // DEBUGGING
						continue outerloop;
					}
				} 
				Id neighborID = neighbor.getId();
				Point neighborPt = neighborID.getPt();
			
				int tentGScore = current.getId().getG() + hScoreFunc(current.getId().getPt(), neighborPt);
				int neighborG = neighborID.getG();

				// System.out.print(tentGScore); // DEBUGGING
				// System.out.print(neighborID.getG()); //DEBUGGING
				/*
				if(openSet.size() == 0)
				{
					if (tentGScore < neighborG)
					{
						neighbor.setFrom(current);
						neighborID.setG(tentGScore);
						neighborID.setF(fScoreFunc(tentGScore, hScoreFunc(neighbor.getId().getPt(), goal)));
						System.out.print(" tentGScore < neightbor G");
					}
				}*/
				for(int j = 0; j < openSet.size(); j ++)
				{
					System.out.print(" got to Openset loop. ");
					if((neighbor != openSet.get(j)) || (tentGScore < (neighborID.getG())))
					{
						neighbor.setFrom(current);
						neighborID.setG(tentGScore);
						neighborID.setF(fScoreFunc(tentGScore, hScoreFunc(neighbor.getId().getPt(), goal)));
						//System.out.print(" Neightbor not in Openset OR tentGScore < neightbor G");
						if(neighbor != openSet.get(j))
						{
							openSet.add(neighbor);
							//System.out.print(" OpenSet added a neighbor. ");
						}
					}	
				}
				
			}
		}
		return new LinkedList<Point>(); // empty list // Failure
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
		int currX = currentPt.x();
		int currY = currentPt.y();

		Point top = new Point(currentPt.x(), currentPt.y()-1);
		if(world.withinBounds(currentPt) &&
			!this.seeObstacles(top))
		{
			Node<Id> topNode = WorldModel.getCell(world.getNodes(), top);
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
			Node<Id> leftNode = world.getNode(world.getNodes(),left); 
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
			Node<Id> botNode = world.getNode(world.getNodes(),bottom); 
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
			Node<Id> rightNode = world.getNode(world.getNodes(),right); 
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
		path.add(0, pt);
		if(current.getFrom() != null)
		{
			reconstructPath(current.getFrom(), path);
		}
		return path;
	}

}