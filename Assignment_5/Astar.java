//A* Algorithm in Java 

import java.util.LinkedList;
import java.util.List;
import java.lang.Math.*;
import java.util.Collections;
import java.util.Comparator;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;

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
	{ 
		LinkedList<Node<Id>> openSet = new LinkedList<Node<Id>>();
		LinkedList<Node<Id>> closedSet = new LinkedList<Node<Id>>();
		Id startId = new Id(start, 0, fScoreFunc(0, hScoreFunc(start, goal)), hScoreFunc(start, goal));
		Node<Id> startNode = new Node<Id>(startId, null);
		openSet.add(startNode);
		Id goalId = new Id(goal, 0, fScoreFunc(0,hScoreFunc(goal, start)), hScoreFunc(goal, start));
		Node<Id> goalNode = new Node<Id>(goalId, null);
		openSet.add(startNode);
		while(true)
		{
			Node<Id> current = null;
			if (openSet.size() ==0)
			{
				System.out.println("this rubbish code isn't working because there's no route");
			}
			Iterator<Node<Id>> iter = openSet.iterator();
			while(iter.hasNext())
			{
				if(current==null || iter.next().getId().getF() < current.getId().getF())
				{
					current = iter.next();
				}
				if(current.getId().getPt().equals(goal))
				{
					break;
				}
				//iter.next();
				iter.remove();
				closedSet.add(current);
				
				for (Node<Id> neighbor:findNeighbors(current))
				{
					if(neighbor==null)
					{
						continue;
					}
					int tentG = current.getId().getG() + 1;
					if(tentG<neighbor.getId().getG())
					{
						openSet.remove(neighbor);
						closedSet.remove(neighbor);
					}
					if(!openSet.contains(neighbor) && !closedSet.contains(neighbor))
					{
						neighbor.getId().setG(tentG);
						neighbor.getId().setH(hScoreFunc(neighbor.getId().getPt(), goal));
						neighbor.getId().setF(neighbor.getId().getG() + neighbor.getId().getH());
						neighbor.setFrom(current);
						openSet.add(neighbor);
					}
				}
			}
			LinkedList<Node<Id>> output = new LinkedList<Node<Id>>(); 
			current = goalNode;
			while(current.getFrom() !=null)
			{
				output.add(current);
				current = current.getFrom();
			}
			output.add(startNode);
			return NodeToPoint(output);
		}
	}	
	
	public LinkedList<Point> NodeToPoint(LinkedList<Node<Id>> list)
	{
		LinkedList<Point> output = new LinkedList<Point>();
		for(Node<Id> node : list)
		{
			output.add(node.getId().getPt());
		}
		return output;
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
			Id topId = new Id(top, 1, ftop, htop);
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
			Id leftId = new Id(left, 1, fleft, hleft);
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
			Id botId = new Id(bottom, 1, fBot, hBot);
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
			Id rightId = new Id(right, 1, fR, hR);
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