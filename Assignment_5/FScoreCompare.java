import java.util.Comparator;

public class FScoreCompare
	implements Comparator<Node<Id>>
{
	public int compare(Node<Id> one, Node<Id> two)
	{
		int oneF = one.getId().getF();
		int twoF = two.getId().getF();
      return oneF - twoF;
		// will return a negative int if first < second; pos int if first > second
	}
}