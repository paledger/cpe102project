import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Before;

public class TestCases
{
	private static final double DELTA = 0.00001;
	private Entity none;

	@Test
	public void testPointArgs()
	{
        Point pt = new Point(3, 4);
        assertEquals(pt.x, 3);
        assertEquals(pt.y, 4);
        assertEquals(pt.x(), 3);
        assertEquals(pt.y(), 4);
    }

	@Test
	public void testWorldModelArgs()
	{
		Point pt = new Point(1, 1);
		Entity test1 = new Ore("Test1", pt, 10);
		Grid test = new Grid(2, 2, test1);

		WorldModel world = new WorldModel(2, 2, test);

		Grid expected = new Grid(2, 2, test1);

		assertEquals(world.background(), test);
		assertEquals(world.num_cols(), 2);
		assertEquals(world.num_rows(), 2);
	}
/*
	@Test
	public void testWorldModel_occupancy()
	{
		Grid grid = new Grid(2, 2, none);
		WorldModel test_world = new WorldModel(3, 3, grid);
		Grid expected = new Grid(3, 3, none);
		assertEquals(test_world.occupancy(), expected);
	}
*/

	@Test
	public void testVein()
	{
		Point pt = new Point(4, 90);
        Vein test = new Vein("test", 10, pt, 1);
        assertEquals(test.name, "test");
        assertEquals(test.getPosition().x(), 4);
        assertEquals(test.getPosition().y(), 90);
        assertEquals(test.getResourceDistance(), 1);
	}

	@Test
	public void testResourceDistance()
	{
		Point pt = new Point(40, 20);
		ResourceDistance tester = new ResourceDistance("tester", pt, 33, 44);

	    assertEquals(tester.getName(), "tester");
	    assertEquals(tester.getPosition().x(), 40);
	    assertEquals(tester.getPosition().y(), 20);
	    assertEquals(tester.getRate(), 33);
	    assertEquals(tester.getResourceDistance(), 44);
	}

	@Test
	public void testQuake()
	{
		Point pt = new Point(5, 9);
		Quake quake = new Quake("quake", pt, 70);
		assertEquals(quake.getName(), "quake");
		assertEquals(quake.getPosition().x(), 5);
		assertEquals(quake.getPosition().y(), 9);
		assertEquals(quake.getAnimationRate(), 70);
	}

    @Test
	public void testPoint_withinBounds()
	{
		Point ent_pt = new Point(1, 1);
        Entity test1 = new Ore("Test1", ent_pt, 10);
		Grid test = new Grid(2, 2, test1);

		WorldModel world = new WorldModel(2, 2, test);

		Point pt = new Point(0, 0);
		assertTrue(pt.withinBounds(world));
	}

/*
    @Test
	public void testPoint_isOccupied()
	{
		Point ent_pt = new Point(0, 0);
        Entity test1 = new Ore("Test1", ent_pt, 10);
		Grid test = new Grid(10, 10, test1);

		WorldModel world = new WorldModel(10, 10, test);

		Point pt = new Point(0, 0);
		assertFalse(pt.isOccupied(world));
	}
*/
	@Test
	public void testPoint_distanceSq()
	{
		Point p1 = new Point(0, 0);
		Point p2 = new Point(3, 4);
		int test = p1.distanceSq(p2);
		assertEquals(test, 25);
	}

	@Test
	public void testPoint_adjacent()
	{
		Point p1 = new Point(0, 0);
		Point p2 = new Point(0, 1);
		boolean test = p1.adjacent(p2);
		assertTrue(test);
	}

}