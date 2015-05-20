import java.util.Scanner;
import java.lang.Integer.*;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.lang.String.*;
import java.util.LinkedList;
import processing.core.*;


public class SaveLoad
	extends PApplet
{

	private final static int PROPERTY_KEY = 0;

	private final static String BGND_KEY = "background";
	private final static int BGND_NUM_PROPERTIES = 4;
	private final static int BGND_NAME = 1;
	private final static int BGND_COL = 2;
	private final static int BGND_ROW = 3;

	private final static String MINER_KEY = "miner";
	private final static int MINER_NUM_PROPERTIES = 7;
	private final static int MINER_NAME = 1;
	private final static int MINER_LIMIT = 4;
	private final static int MINER_COL = 2;
	private final static int MINER_ROW = 3;
	private final static int MINER_RATE = 5;
	private final static int MINER_ANIMATION_RATE = 6;

	private final static String OBSTACLE_KEY = "obstacle";
	private final static int OBSTACLE_NUM_PROPERTIES = 4;
	private final static int OBSTACLE_NAME = 1;
	private final static int OBSTACLE_COL = 2;
	private final static int OBSTACLE_ROW = 3;

	private final static String ORE_KEY = "ore";
	private final static int ORE_NUM_PROPERTIES = 5;
	private final static int ORE_NAME = 1;
	private final static int ORE_COL = 2;
	private final static int ORE_ROW = 3;
	private final static int ORE_RATE = 4;

	private final static String SMITH_KEY = "blacksmith";
	private final static int SMITH_NUM_PROPERTIES = 7;
	private final static int SMITH_NAME = 1;
	private final static int SMITH_COL = 2;
	private final static int SMITH_ROW = 3;
	private final static int SMITH_LIMIT = 4;
	private final static int SMITH_RATE = 5;
	private final static int SMITH_REACH = 6;

	private final static String VEIN_KEY = "vein";
	private final static int VEIN_NUM_PROPERTIES = 6;
	private final static int VEIN_NAME = 1;
	private final static int VEIN_RATE = 4;
	private final static int VEIN_COL = 2;
	private final static int VEIN_ROW = 3;
	private final static int VEIN_REACH = 5;

	// Type of file is listed as "type" momentarily
	// Type of properties is listed as "PropType" momentarily--it's probably a list of some sort
	//type of "run" is boolean
	public void saveWorld(WorldModel world, File file)
	{
		saveEntities(world, file);
		saveBackground(world, file);
	}

	public void saveEntities(WorldModel world, File file)
	{
		for(Entity entity:world.entities())
		{
			Class<?> eClass = entity.getClass();
			file.write(entity.entityString() + "\n");
		}
	}
	
	public void saveBackground(WorldModel world, File file)
	{
		FileWriter writer = new FileWriter(file);
		for(int row = 0; row < world.num_rows(); row ++)
		{
			for(int col = 0; col < world.num_cols(); col ++)
			{
				writer.write("background" + 
					(world.getBackground(new Point(col, row))));
			}
		}
		
	}
	public void loadWorld(WorldModel world, HashMap<String, LinkedList<PImage>> iStore,
		File file, boolean run, Scanner in)
	{
		run = false;
		while(in.hasNextLine())
		{
			String[] properties = in.next().split("\\s");
			String[] empty = {};
            if(!properties.equals(empty))
            {
            	if(properties[PROPERTY_KEY] == BGND_KEY)
            	{
            		addBackground(world, properties, iStore);
            	}
            	else
            	{
            		addEntity(world, properties, iStore, run);
            	}
            }
		}
	}
	
	public void addBackground(WorldModel world, String[] properties, HashMap<String, LinkedList<PImage>> iStore)
	{
		if (properties.length >= BGND_NUM_PROPERTIES)
		{
			//figure out these when type of Properties is finalized
			Point pt = new Point(Integer.getInteger(properties[BGND_COL]), Integer.getInteger(properties[BGND_ROW]));
			String name = properties[BGND_NAME];
			Background bg = new Background(name, ImageStore.getImages(iStore, name));
			world.setBackground(pt, bg);
		}
	}
	
	public void addEntity(WorldModel world, String[] properties, HashMap<String, LinkedList<PImage>> iStore, boolean run)
	{
		Entity newEntity = this.createFromProperties(properties, iStore);
		if(newEntity != null)
		{
			world.addEntity(newEntity);
			if(run)
			{
				newEntity.scheduleEntity(world, iStore);
			}
		}
	}
	
	public Entity createFromProperties(String[] properties, HashMap<String, LinkedList<PImage>> iStore)
	{
		String key = properties[PROPERTY_KEY];
		String[] empty = {};
		if(!properties.equals(empty))
		{
			if(key == MINER_KEY)
			{
				return createMiner(properties, iStore);
			}
			else if(key == VEIN_KEY)
			{
				return createVein(properties, iStore);
			}
			else if(key == ORE_KEY)
			{
				return createOre(properties, iStore);
			}
			else if(key == SMITH_KEY)
			{
				return createBlacksmith(properties, iStore);
			}
			else if(key == OBSTACLE_KEY)
			{
				return createObstacle(properties, iStore);
			}
			return null;
		}

	}
	
	public Miner createMiner(String[] properties, HashMap<String, LinkedList<PImage>> iStore)
	{
		if(properties.length == MINER_NUM_PROPERTIES)
		{
			Miner miner = new MinerNotFull(properties[MINER_NAME],
				Integer.getInteger(properties[MINER_LIMIT]),
				new Point(Integer.getInteger(properties[MINER_COL]), 
					Integer.getInteger(properties[MINER_ROW])),
				Integer.getInteger(properties[MINER_RATE]),
				ImageStore.getImages(iStore, properties[PROPERTY_KEY]),
				Integer.getInteger(properties[MINER_ANIMATION_RATE]));
			return miner;
		}
		return null;
	}

	public Vein createVein(String[] properties, HashMap<String, LinkedList<PImage>> iStore)
	{
		if(properties.length == VEIN_NUM_PROPERTIES)
		{
			Vein vein = new Vein(properties[VEIN_NAME],
				Integer.getInteger(properties[VEIN_RATE]),
				new Point(Integer.getInteger(properties[VEIN_COL]), 
					Integer.getInteger(properties[VEIN_ROW])),
				ImageStore.getImages(iStore, properties[PROPERTY_KEY]),
				Integer.getInteger(properties[VEIN_REACH]));
			return vein;
		}
		return null;
	}

	public Ore createOre(String[] properties, HashMap<String, LinkedList<PImage>> iStore)
	{
		if(properties.length == ORE_NUM_PROPERTIES)
		{
			Ore ore = new Ore(properties[ORE_NAME],
				new Point(Integer.getInteger(properties[ORE_COL]), 
					Integer.getInteger(properties[ORE_ROW])),
				ImageStore.getImages(iStore, properties[PROPERTY_KEY]),
				Integer.getInteger(properties[ORE_RATE]));
			return ore;
		}
		return null;
	}

	public Blacksmith createBlacksmith(String[] properties, HashMap<String, LinkedList<PImage>> iStore)
	{
		if(properties.length == SMITH_NUM_PROPERTIES)
		{
			Blacksmith blacksmith = new Blacksmith(properties[SMITH_NAME],
				new Point(Integer.getInteger(properties[SMITH_COL]), 
					Integer.getInteger(properties[SMITH_ROW])),
				ImageStore.getImages(iStore, properties[PROPERTY_KEY]),
				Integer.getInteger(properties[SMITH_LIMIT]),
				Integer.getInteger(properties[SMITH_RATE]),
				Integer.getInteger(properties[SMITH_REACH]));
			return blacksmith;
		}
		return null;
	}

}