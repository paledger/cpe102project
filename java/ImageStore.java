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
	private final static String DEFAULT_IMAGE_NAME = "background_default";
	private final static int[] DEFAULT_IMAGE_COLOR = {128, 128, 128, 0};

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
 	public static HashMap<String, List<String>> processLines(Scanner in)
 	{
		HashMap<String, List<String>> iStore = new HashMap<String, List<String>>();
		LinkedList<String>  blacksmith = new LinkedList<String>();
		LinkedList<String>  blob = new LinkedList<String>();
		LinkedList<String>  grass = new LinkedList<String>();
		LinkedList<String>  miner = new LinkedList<String>();
		LinkedList<String>  obstacle = new LinkedList<String>();
		LinkedList<String>  ore = new LinkedList<String>();
		LinkedList<String>  quake = new LinkedList<String>();
		LinkedList<String>  rocks = new LinkedList<String>();
		LinkedList<String>  vein = new LinkedList<String>();
		LinkedList<String>  background_default = new LinkedList<String>();
		while(in.hasNextLine())
		{
			String[] words = in.nextLine().split("\\s");
			if(words[0] == "blacksmith")
			{
				blacksmith.add(words[1]);
			}
			if(words[0] == "blob")
			{
				blob.add(words[1]);
			}
			if(words[0] == "grass")
			{
				grass.add(words[1]);
			}
			if(words[0] == "miner")
			{
				miner.add(words[1]);
			}
			if(words[0] == "obstacle")
			{
				obstacle.add(words[1]);
			}
			if(words[0] == "ore")
			{
				ore.add(words[1]);
			}
			if(words[0] == "quake")
			{
				quake.add(words[1]);
			}
			if(words[0] == "rocks")
			{
				rocks.add(words[1]);
			}
			if(words[0] == "vein")
			{
				vein.add(words[1]);
			}
			if(words[0] == "background_default")
			{
				background_default.add(words[1]);
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

/*
	private static boolean verifyArgs(String[] args)
	{
		return args.length >= 1;
	}

	public Rect createDefaultImage(int tile_width, int tile_height)
	{
		fill(DEFAULT_IMAGE_COLOR[0], DEFAULT_IMAGE_COLOR[1], 
			DEFAULT_IMAGE_COLOR[2], DEFAULT_IMAGE_COLOR[3]);
		Rect rect = new Rect(0, 0, tile_width, tile_height);
     	return rect;
	}


	public ArrayList<PImage> loadImages(String filename, int tile_width, int tile_height)
	{
		ArrayList<PImage> list = new ArrayList<PImage>();
		return list;

	}



	public void processImageLine(List<String> images, String line)
	{
	}

	public int getImagesInternal(List<String> images, String key)
	{
		for(int i = 0; i < images.size(); i ++)
		{
			if(images.get(i) == key)
			{
				return images.indexOf(key);
			}
			else
			{
				return 0;
			}
		}
		return 0;
	}

	public int getImages(List<String> images, String key)
	{
		for(int i = 0; i < images.size(); i ++)
		{
			if(images.get(i) == key)
			{
				return images.indexOf(key);
			}
			else
			{
				return images.indexOf(DEFAULT_IMAGE_NAME);
			}
		}
		return 0;
	}
*/
}