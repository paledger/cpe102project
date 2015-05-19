import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageStore
	extends PApplet
{
	public final static String DEFAULT_IMAGE_NAME = "background_default";
	public final static int[] DEFAULT_IMAGE_COLOR = {128, 128, 128, 0};

/*
	public List<String> readLines() 
	{
		File file = new File("imagelist");
		FileReader fr = new FileReader(file);
	   try(BufferedReader br = new BufferedReader(fr))
	   {
			String line;
			List<String> output = new LinkedList<String>();
			while((line = br.readLine()) != null)
			{
				output.add(line);
			}
			return output;
		}
	}
*/
 	public HashMap<String, LinkedList<PImage>> processLines(Scanner in)
 	{
		HashMap<String, LinkedList<PImage>> iStore = new HashMap<String, LinkedList<PImage>>();
		LinkedList<PImage>  blacksmith = new LinkedList<PImage>();
		LinkedList<PImage>  blob = new LinkedList<PImage>();
		LinkedList<PImage>  grass = new LinkedList<PImage>();
		LinkedList<PImage>  miner = new LinkedList<PImage>();
		LinkedList<PImage>  obstacle = new LinkedList<PImage>();
		LinkedList<PImage>  ore = new LinkedList<PImage>();
		LinkedList<PImage>  quake = new LinkedList<PImage>();
		LinkedList<PImage>  rocks = new LinkedList<PImage>();
		LinkedList<PImage>  vein = new LinkedList<PImage>();
		LinkedList<PImage>  background_default = new LinkedList<PImage>();
		while(in.hasNextLine())
		{
			String[] words = in.nextLine().split("\\s");
			if(words[0] == "blacksmith")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				blacksmith.add(image);
			}
			if(words[0] == "blob")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				blob.add(image);
			}
			if(words[0] == "grass")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				grass.add(image);
			}
			if(words[0] == "miner")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				miner.add(image);
			}
			if(words[0] == "obstacle")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				obstacle.add(image);
			}
			if(words[0] == "ore")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				ore.add(image);
			}
			if(words[0] == "quake")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				quake.add(image);
			}
			if(words[0] == "rocks")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				rocks.add(image);
			}
			if(words[0] == "vein")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				vein.add(image);
			}
			if(words[0] == "background_default")
			{
				PImage image = new PImage();
				image = loadImage(words[1]);
				background_default.add(image);
			}
			
			iStore.put("blacksmith", blacksmith);
			iStore.put("blob", blob);
			iStore.put("grass", grass);
			iStore.put("miner", miner);
			iStore.put("obstacle", obstacle);
			iStore.put("ore", ore);
	      iStore.put("quake", quake);
	      iStore.put("rocks", rocks);
	      iStore.put("vein", vein);
	      iStore.put("background_default", background_default);
	      
		}
		return iStore;
 	}

	public Rect createDefaultImage(int tile_width, int tile_height)
	{
		fill(DEFAULT_IMAGE_COLOR[0], DEFAULT_IMAGE_COLOR[1], 
			DEFAULT_IMAGE_COLOR[2], DEFAULT_IMAGE_COLOR[3]);
		Rect rect = new Rect(0, 0, tile_width, tile_height);
     	return rect;
	}

/*	public ArrayList<PImage> loadImages(String filename, int tile_width, int tile_height)
	{
		ArrayList<PImage> list = new ArrayList<PImage>();
		return list;
	}
	Original Python::
def load_images(filename, tile_width, tile_height):
   images = {}
   with open(filename) as fstr:
      for line in fstr:
         process_image_line(images, line)

   if DEFAULT_IMAGE_NAME not in images:
      default_image = create_default_image(tile_width, tile_height)
      images[DEFAULT_IMAGE_NAME] = [default_image]

   return images
	
	public void processImageLine(List<String> images, String line)
	{
	}
	
	/// functionality moved into processLines
	*/

	public LinkedList<PImage> getImagesInternal(HashMap<String, LinkedList<PImage>> images, String key)
	{
		if(images.containsKey(key))
		{
			return images.get(key);
		}
		else
		{
			LinkedList<PImage> output = new LinkedList<PImage>();
			return output;
		}
	}

	public static LinkedList<PImage> getImages(HashMap<String, LinkedList<PImage>> images, String key)
	{
		if(images.containsKey(key))
		{
			return images.get(key);
		}
		else
		{
			return images.get(DEFAULT_IMAGE_NAME);
		}
	}
}