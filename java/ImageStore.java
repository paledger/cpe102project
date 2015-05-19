import processing.core.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class ImageStore
	extends PApplet
{
	private final static String DEFAULT_IMAGE_NAME = "background_default";
	private final static int[] DEFAULT_IMAGE_COLOR = {128, 128, 128, 0};



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
		

	}

 	private static void processLines(Scanner in)
 	{
		String name = "None";
		while(in.hasNextLine())
		{
			String[] words = in.nextLine().split("\\s");
		}
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
	}
}