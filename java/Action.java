import java.util.List;

@FunctionalInterface
public interface Action
{
   public List<Point> apply(int o1);	
}