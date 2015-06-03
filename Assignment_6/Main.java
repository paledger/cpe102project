import processing.core.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.LinkedList;

public class Main extends PApplet
{
   private static final int WORLD_WIDTH_SCALE = 2;
   private static final int WORLD_HEIGHT_SCALE = 2;

   private static final int SCREEN_WIDTH = 640;
   private static final int SCREEN_HEIGHT = 480;
   private static final int TILE_WIDTH = 32;
   private static final int TILE_HEIGHT = 32;

   private static final int TIMER_ACTION_DELAY = 100;

   private static final String IMAGE_LIST_FILE_NAME = "imagelist";
   private static final String DEFAULT_IMAGE_NAME = "background_default";
   private static final int DEFAULT_IMAGE_COLOR = 0x808080;

   private static final String SAVE_FILE_NAME = "gaia.sav";

   private ImageStore imageStore;
   private long next_time;
   private WorldModel world;
   private WorldView view;
   private Background backgroundHole;

   // Mouse Pressed variables 
   private int mouseDX;
   private int mouseDY;


   public void setup()
   {
      size(SCREEN_WIDTH, SCREEN_HEIGHT);
      imageStore = new ImageStore(
         createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);

      int num_cols = SCREEN_WIDTH / TILE_WIDTH * WORLD_WIDTH_SCALE;
      int num_rows = SCREEN_HEIGHT / TILE_HEIGHT * WORLD_HEIGHT_SCALE;

      mouseDX = 0;
      mouseDY = 0;

      // create default background
      Background background = createDefaultBackground(imageStore);
      //backgroundHole = createHoleBackground(imageStore);

      // create world model
      world = new WorldModel(num_rows, num_cols, background);

      // load world
      Map<String, PropertyParser> parsers = buildPropertyParsers(world,
         imageStore, System.currentTimeMillis());
      loadWorld(world, SAVE_FILE_NAME, imageStore, parsers);

      // create world view
      view = new WorldView(SCREEN_WIDTH / TILE_WIDTH,
         SCREEN_HEIGHT / TILE_HEIGHT, this, world, TILE_WIDTH, TILE_HEIGHT);

      // update view?

      next_time = System.currentTimeMillis() + TIMER_ACTION_DELAY;
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         world.updateOnTime(time);
         next_time = time + TIMER_ACTION_DELAY;
      }

      view.drawViewport();
   }

   public void createHole(WorldModel world, int mouseX, int mouseY)
   {
      int ptX = floor(mouseX/32);
      int ptY = floor(mouseY/32);
		Point pt1 = new Point(ptX, ptY);
		Point pt2 = new Point(ptX+1, ptY);
		Point pt3 = new Point(ptX, ptY+1);
		Point pt4 = new Point(ptX+1, ptY+1);
		if(ptX<world.getNumCols()+1 && ptX >= 0 && ptY<world.getNumRows()+1 && ptY >= 0)
		{
			if(world.hole[ptY][ptX]==false&&world.hole[ptY][ptX+1]==false
				&&world.hole[ptY+1][ptX]==false&&world.hole[ptY+1][ptX+1]==false)
			{
	      world.setBackground(pt1, createHole1Background(imageStore));
	      world.setBackground(pt2, createHole2Background(imageStore));
	      world.setBackground(pt3, createHole3Background(imageStore));
	      world.setBackground(pt4, createHole4Background(imageStore));
			world.hole[ptY][ptX] = true;
			world.hole[ptY][ptX+1] = true;
			world.hole[ptY+1][ptX] = true;
			world.hole[ptY+1][ptX+1] = true;
			}
		}
   }

   public void mousePressed()
   {
      createHole(world, mouseDX+mouseX, mouseDY+mouseY);
		//createHole(world, mouseX, mouseY);
   }

   public void keyPressed()
   {
      if (key == CODED)
      {
         int dx = 0;
         int dy = 0;
         switch (keyCode)
         {
				//HITTING DOWN MOVES IT LEFT AND DOWN
            case UP:
               dy = -1;
					//if(mouseDY>=0&&mouseDY<world.getNumRows()*32)
					//{
						mouseDY-=32;
						//}
               break;
            case DOWN:
               dy = +1;
					//if(mouseDY>=0&&mouseDY<world.getNumRows()*32)
					//{
						mouseDY+=32;
						//}
					break;
            case LEFT:
               dx = -1;
					//if(mouseDX>=0&&mouseDX<world.getNumCols()*32)
					//{
						mouseDX-=32;
						//}
               break;
            case RIGHT:
               dx = +1;
					//if(mouseDX>=0&&mouseDX<world.getNumCols()*32)
					//{
						mouseDX+=32;
						//}					
               break;
         }
         view.updateView(dx, dy);
      }
   }


   private static Background createDefaultBackground(ImageStore imageStore)
   {
      List<PImage> bgndImgs = imageStore.get(DEFAULT_IMAGE_NAME);
      return new Background(DEFAULT_IMAGE_NAME, bgndImgs);
   }

   private static Background createHole1Background(ImageStore imageStore)
   {
      List<PImage> bgndImgs = imageStore.get("hole1");
      return new Background("hole1", bgndImgs);
   }
	
   private static Background createHole2Background(ImageStore imageStore)
   {
      List<PImage> bgndImgs = imageStore.get("hole2");
      return new Background("hole2", bgndImgs);
   }
	
   private static Background createHole3Background(ImageStore imageStore)
   {
      List<PImage> bgndImgs = imageStore.get("hole3");
      return new Background("hole3", bgndImgs);
   }
	
   private static Background createHole4Background(ImageStore imageStore)
   {
      List<PImage> bgndImgs = imageStore.get("hole4");
      return new Background("hole4", bgndImgs);
   }

   private static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(TILE_WIDTH, TILE_HEIGHT, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }


   private static void loadImages(String filename, ImageStore imageStore,
      PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         ImageStore.loadImages(in, imageStore, TILE_WIDTH,
               TILE_HEIGHT, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static void loadWorld(WorldModel world, String filename,
      ImageStore imageStore, Map<String, PropertyParser> parsers)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         WorldLoad.load(in, world, imageStore, parsers);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static final String BGND_KEY = "background";
   private static final int BGND_NUM_PROPERTIES = 4;
   private static final int BGND_NAME = 1;
   private static final int BGND_COL = 2;
   private static final int BGND_ROW = 3;

   private static final String MINER_KEY = "miner";
   private static final int MINER_NUM_PROPERTIES = 7;
   private static final int MINER_NAME = 1;
   private static final int MINER_LIMIT = 4;
   private static final int MINER_COL = 2;
   private static final int MINER_ROW = 3;
   private static final int MINER_RATE = 5;
   private static final int MINER_ANIMATION_RATE = 6;

   private static final String OBSTACLE_KEY = "obstacle";
   private static final int OBSTACLE_NUM_PROPERTIES = 4;
   private static final int OBSTACLE_NAME = 1;
   private static final int OBSTACLE_COL = 2;
   private static final int OBSTACLE_ROW = 3;

   private static final String ORE_KEY = "ore";
   private static final int ORE_NUM_PROPERTIES = 5;
   private static final int ORE_NAME = 1;
   private static final int ORE_COL = 2;
   private static final int ORE_ROW = 3;
   private static final int ORE_RATE = 4;

   private static final String SMITH_KEY = "blacksmith";
   private static final int SMITH_NUM_PROPERTIES = 4;
   private static final int SMITH_NAME = 1;
   private static final int SMITH_COL = 2;
   private static final int SMITH_ROW = 3;

   private static final String VEIN_KEY = "vein";
   private static final int VEIN_NUM_PROPERTIES = 6;
   private static final int VEIN_NAME = 1;
   private static final int VEIN_RATE = 4;
   private static final int VEIN_COL = 2;
   private static final int VEIN_ROW = 3;
   private static final int VEIN_REACH = 5;

   private static Map<String, PropertyParser> buildPropertyParsers(
      WorldModel world, ImageStore imageStore, long time)
   {
      Map<String, PropertyParser> parsers = new HashMap<>();

      parsers.put(BGND_KEY, properties -> {
            if (properties.length >= BGND_NUM_PROPERTIES)
            {
               Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                  Integer.parseInt(properties[BGND_ROW]));
               String name = properties[BGND_NAME];
               world.setBackground(pt, new Background(name,
                  imageStore.get(name)));
            }
         });

      parsers.put(MINER_KEY, properties -> {
            if (properties.length == MINER_NUM_PROPERTIES)
            {
               Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                  Integer.parseInt(properties[MINER_ROW]));
               Actor entity = new MinerNotFull(properties[MINER_NAME],
                  pt,
                  Integer.parseInt(properties[MINER_RATE]),
                  Integer.parseInt(properties[MINER_ANIMATION_RATE]),
                  Integer.parseInt(properties[MINER_LIMIT]),
                  imageStore.get(MINER_KEY));
               world.addEntity(entity);
               entity.schedule(world, time + entity.getRate(), imageStore);
            }
         });

      parsers.put(OBSTACLE_KEY, properties -> {
            if (properties.length == OBSTACLE_NUM_PROPERTIES)
            {
               Point pt = new Point(
                  Integer.parseInt(properties[OBSTACLE_COL]),
                  Integer.parseInt(properties[OBSTACLE_ROW]));
               WorldEntity entity = new Obstacle(properties[OBSTACLE_NAME],
                  pt,
                  imageStore.get(OBSTACLE_KEY));
               world.addEntity(entity);
            }
         });

      parsers.put(ORE_KEY, properties -> {
            if (properties.length == ORE_NUM_PROPERTIES)
            {
               Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                  Integer.parseInt(properties[ORE_ROW]));
               Actor entity = new Ore(properties[ORE_NAME],
                  pt,
                  Integer.parseInt(properties[ORE_RATE]),
                  imageStore.get(ORE_KEY));
               world.addEntity(entity);
               entity.schedule(world, time + entity.getRate(), imageStore);
            }
         });

      parsers.put(SMITH_KEY, properties -> {
            if (properties.length >= SMITH_NUM_PROPERTIES)
            {
               Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                  Integer.parseInt(properties[SMITH_ROW]));
               WorldEntity entity = new Blacksmith(properties[SMITH_NAME],
                  pt,
                  imageStore.get(SMITH_KEY));
               world.addEntity(entity);
            }
         });

      parsers.put(VEIN_KEY, properties -> {
            if (properties.length == VEIN_NUM_PROPERTIES)
            {
               Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                  Integer.parseInt(properties[VEIN_ROW]));
               Actor entity = new Vein(properties[VEIN_NAME],
                  pt,
                  Integer.parseInt(properties[VEIN_RATE]),
                  Integer.parseInt(properties[VEIN_REACH]),
                  imageStore.get(VEIN_KEY));
               world.addEntity(entity);
               entity.schedule(world, time + entity.getRate(), imageStore);
            }
         });

      return parsers;
   }

   public static void main(String[] args)
   {
      PApplet.main("Main");
   }
}
