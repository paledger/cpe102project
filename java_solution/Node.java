import java.util.function.Function;

public class Node<Id>
{
	protected Id id;
	protected Node<Id> cameFrom;
	
	public Node(Id id, Node<Id> cameFrom)
	{
		this.id  = id;
		this.cameFrom = cameFrom;
	}
	
	public Id getId()
	{
		return this.id;
	}
	public Node<Id> getFrom()
	{
		return this.cameFrom;
	}
	public void setFrom(Node<Id> newNode)
	{
		cameFrom = newNode;
	}
	
	public void setId(Id newid)
	{
		id = newid;
	}

}
