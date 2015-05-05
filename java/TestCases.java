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

	@Test
	public void testActionable()
	{
		String name = "Name";
		Point pt = new Point(0,1);
		int rate = 1;
		Actionable action = new Actionable(name, pt, rate);
		assertEquals(action.name, "Name");
		assertEquals(action.position.x(), 0);
		assertEquals(action.position.y(), 1);
		assertEquals(action.rate, 1);
	}
	
	@Test
	public void testSign()
	{
		Actions test = new Actions();
		assertEquals(test.sign(1),1);
		assertEquals(test.sign(-5),-1);
		assertEquals(test.sign(0),0);
	}
	
	@Test
	public void testAnimated()
	{
		String name = "Name";
		Point pt = new Point(0,1);
		int rate = 1;
		int arate = 500;
		Animated test = new Animated(name, pt, rate, arate);
		assertEquals(test.name, "Name");
		assertEquals(test.position.x(), 0);
		assertEquals(test.position.y(), 1);
		assertEquals(test.rate, 1);
		assertEquals(test.animationRate, 500);
		
		assertEquals(test.getAnimationRate(),500);
	}
	
	@Test
	public void testBackground()
	{
		String name = "Name";
		Background test = new Background(name);
		assertEquals(test.name, "Name");
	}
	
	@Test
	public void testBlacksmith()
	{
		String name = "Name";
		Point pt = new Point(0,1);
		int resourceLimit = 1;
		int rate = 1;
		Blacksmith test = new Blacksmith(name,pt,resourceLimit,rate,500);
		assertEquals(test.position.x(), 0);
		assertEquals(test.position.y(), 1);
		assertEquals(test.rate, 1);
		assertEquals(test.resourceLimit,1);
		assertEquals(test.resourceDistance,1);
		
		assertEquals(test.getResourceCount(),0);
		assertEquals(test.getResourceLimit(),1);
		
		test.setResourceCount(10);
		assertEquals(test.resourceCount,10);
	}
	
	@Test
	public void testDistPair()
	{
		String name = "Name";
		Point pt = new Point(0,1);
		int rate = 5000;
		Entity testEntity = new Ore(name,pt,rate);
		double testDist = 10;
		DistPair test = new DistPair(testEntity,testDist);
		assertEquals(test.entity.name, "Name");
		assertEquals(test.entity.position.x(),0);
		assertEquals(test.entity.position.y(),1);
		assertEquals(test.entity.rate,5000);
		assertEquals(test.dist, 10, DELTA);
		
		assertEquals(test.getEnt().name,"Name");
		assertEquals(test.getEnt().position.x(),0);
		assertEquals(test.getEnt().position.y(),1);
		assertEquals(test.getEnt().rate,5000);		
		assertEquals(test.getDist(),10, DELTA);
	}
	
	@Test
	public void testEntity()
	{
		String name = "Name";
		Point pt = new Point(0,1);
		int rate = 5000;
		Entity test = new Ore(name,pt,rate);
		assertEquals(test.getName(),"Name");
		
		test.setPosition(new Point(1,2));
		assertEquals(test.position.x(),1);
		assertEquals(test.position.y(),2);
		assertEquals(test.getPosition().x(),1);
		assertEquals(test.getPosition().y(),2);
		assertEquals(test.getRate(),5000);
		
	}
	
	@Test
	public void Grid()
	{
		int width = 1;
		int height = 2;
		String name = "Name";
		Point pt = new Point(0,1);
		int rate = 5000;
		Entity occupant = new Ore(name,pt,rate);
		Grid test = new Grid(width,height,occupant);
		test.createCells();
		assertEquals(test.cells[0].name,"Name");
		assertEquals(test.cells[0].position.x(),0);
		assertEquals(test.cells[0].position.y(),1);
		assertEquals(test.cells[0].rate,5000);
		
		Point getPt = new Point(0,0);
		assertEquals(test.getCell(getPt).name,"Name");
		assertEquals(test.getCell(getPt).position.x(),0);
		assertEquals(test.getCell(getPt).position.y(),1);
		assertEquals(test.getCell(getPt).rate,5000);		
	}
	
	@Test
	public void testMiner()
	{
		String name = "Name";
		String name2 = "Full";
		int resourceLimit = 3;
		Point pt = new Point(0,1);
		int rate = 5000;	
		int aRate = 1;
		Entity occupant = new Ore(name,pt,rate);
		Grid background = new Grid(500,500,occupant);
		WorldModel world = new WorldModel(1,1,background);
		MinerNotFull notfull = new MinerNotFull(name,resourceLimit,pt,rate,aRate);
		MinerFull full = new MinerFull(name2,resourceLimit,pt,rate,aRate);
		Entity miner = notfull.tryTransformMinerNotFull(world);
		Entity miner2 = full.tryTransformMinerFull(world);
		
		assertEquals(miner.getName(),"Name");
		assertEquals(miner2.getName(),"Full");
		assertEquals(full.getResourceCount(),3);
		assertEquals(notfull.getResourceCount(),0);
		assertEquals(full.getResourceLimit(),3);
		notfull.setResourceCount(2);
		assertEquals(notfull.getResourceCount(),2);
	}
	
	@Test
	public void testObstacle()
	{
		String name = "Name";
		Point pt = new Point(0,1);
		int rate = 0;
		Obstacle test = new Obstacle(name,pt,rate);
		assertEquals(test.getName(),"Name");
		assertEquals(test.position.x(), 0);
		assertEquals(test.position.y(), 1);
		assertEquals(test.rate, 0);
	}
	
	@Test
	public void testOre()
	{
		String name = "Name";
		Point pt = new Point(0,1);
		int rate = 5000;
		Ore test = new Ore(name,pt,rate);
		assertEquals(test.name,"Name");
		assertEquals(test.position.x(), 0);
		assertEquals(test.position.y(), 1);
		assertEquals(test.rate, 5000);
	}
	
	@Test
	public void testOreBlob()
	{
		String name = "Name";
		Point pt = new Point(0,1);
		int rate = 5000;
		int aRate = 1;
		OreBlob test = new OreBlob(name,pt,rate,aRate);
		assertEquals(test.name,"Name");
		assertEquals(test.position.x(), 0);
		assertEquals(test.position.y(), 1);
		assertEquals(test.rate, 5000);		
		assertEquals(test.animationRate,1);
		
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
	
	@Test
	public void testPoint_distanceSq()
	{
		Point p1 = new Point(0, 0);
		Point p2 = new Point(3, 4);
		double test = p1.distanceSq(p2);
		assertEquals(test, 25, DELTA);
	}

	@Test
	public void testPoint_adjacent()
	{
		Point p1 = new Point(0, 0);
		Point p2 = new Point(0, 1);
		boolean test = p1.adjacent(p2);
		assertTrue(test);
	}

	
/*	@Test
	public void testPoint_isOccupied()
	{
		Point ent_pt = new Point(0, 0);
      Entity test1 = new Ore("Test1", ent_pt, 10);
		Grid test = new Grid(10, 10, test1);

		WorldModel world = new WorldModel(10, 10, test);

		Point pt = new Point(0, 0);
		assertTrue(pt.isOccupied(world));
	}
	>> NullPointerException
	*/
	
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
	
/*	@Test
	public void testPoint_withinBounds()
	{
		Point pt = new Point()
	} */
>>>>>>> origin/master
}