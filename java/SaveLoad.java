import java.util.Scanner;
import java.lang.Integer.*;
import java.nio.file.Files;
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
		saveBackground(world,file);
	}
	public void saveEntities(WorldModel world, File file)
	{
		for(Entity entity:world.entities())
		{
			//from python:
			//file.write(entity.entity_string() + '\n')
		}
	}
	
	public void saveBackground(WorldModel world, File file)
	{
		for(int row = 0; row < world.num_rows(); row ++)
		{
			for(int col = 0; col < world.num_cols(); col ++)
			{
				//from python:
				//file.write('background '+ worldmodel.get_background(world, point.Point((col, row))) +\' ' + str(col) + ' ' + str(row) + '\n').get_name())))
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
            if(properties.equals(empty))
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
			Point pt = new Point(getInteger(properties[BGND_COL]), getInteger(properties[BGND_ROW]));
			String name = properties[BGND_NAME];
			Background bg = new Background(name, ImageStore.getImages(iStore, name));
			world.setBackground(pt, bg);
		}
	}
	
	public void addEntity(WorldModel world, PropType properties, HashMap<String, LinkedList<PImage>> iStore, boolean run)
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
	
	public void createFromProperties(PropType properties, HashMap<String, LinkedList<PImage>> iStore)
	{
		//from python:
		//what's the type of key?
		/*   key = properties[PROPERTY_KEY]
   if properties:
      if key == MINER_KEY:
         return create_miner(properties, i_store)
      elif key == VEIN_KEY:
         return create_vein(properties, i_store)
      elif key == ORE_KEY:
         return create_ore(properties, i_store)
      elif key == SMITH_KEY:
         return create_blacksmith(properties, i_store)
      elif key == OBSTACLE_KEY:
         return create_obstacle(properties, i_store)
   return None
			*/
	}
	
	public Miner createMiner(String[] properties, HashMap<String, LinkedList<PImage>> iStore)
	{
		/*
   if len(properties) == MINER_NUM_PROPERTIES:
      miner = entities.MinerNotFull(properties[MINER_NAME],
         int(properties[MINER_LIMIT]),
         point.Point(int(properties[MINER_COL]), int(properties[MINER_ROW])),
         int(properties[MINER_RATE]),
         image_store.get_images(i_store, properties[PROPERTY_KEY]),
         int(properties[MINER_ANIMATION_RATE]))
      return miner
   else:
      return None
			*/
	}
}