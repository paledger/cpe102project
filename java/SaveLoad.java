public class SaveLoad
{
	// Type of file is listed as "type" momentarily
	// Type of properties is listed as "PropType" momentarily--it's probably a list of some sort
	//type of "run" is boolean
	public void saveWorld(WorldModel world, Type file)
	{
		saveEntities(world, file);
		saveBackground(world,file);
	}
	public void saveEntities(WorldModel world, Type file)
	{
		for(Entity entity:world.getEntities)
		{
			//from python:
			//file.write(entity.entity_string() + '\n')
		}
	}
	
	public void saveBackground(WorldModel world, Type file)
	{
		for(int row:range(0,world.num_rows))
		{
			for(int col:range(0,world.num_cols))
			{
				//from python:
				//file.write('background '+ worldmodel.get_background(world, point.Point((col, row))) +\' ' + str(col) + ' ' + str(row) + '\n').get_name())))
			}
		}
		
	}
	public void loadWorld(WorldModel world, HashMap<String, LinkedList<PImage>> iStore,
		Type file, boolean run)
	{
		run = false;
		for(String line in )
	}
	
	public void addBackground(WorldModel world, PropType properties, HashMap<String, LinkedList<PImage>> iStore)
	{
		if (properties.size() >= BGND_NUM_PROPERTIES)
		{
			//figure out these when type of Properties is finalized
			Point pt = new Point(int(properties[BGND_COL]), int(properties[BGND_ROW]));
			String name = properties[BGND_NAME];
			Background bg = new Background(name, iStore.getImages(iStore, name));
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
	
	public Miner createMiner(PropType properties, HashMap<String, LinkedList<PImage>> iStore)
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