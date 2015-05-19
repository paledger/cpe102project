import processing.core.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class Main
    extends PApplet
{
	public static boolean RUN_AFTER_LOAD = true;

	public static String IMAGE_LIST_FILE_NAME = "imagelist";
	public static String WORLD_FILE = "gaia.sav";

	private final static String DEFAULT_IMAGE_NAME = "background_default";
	private final static int[] DEFAULT_IMAGE_COLOR = {128, 128, 128, 0};

	public final static int WORLD_WIDTH_SCALE = 2;
	public final static int WORLD_HEIGHT_SCALE = 2;

	public final static int SCREEN_WIDTH = 640;
	public final static int SCREEN_HEIGHT = 480;
	public final static int TILE_WIDTH = 32;
	public final static int TILE_HEIGHT = 32;

	public Background createDefaultBackground(ArrayList<PImage> img)
	{
		Background bg = new Background(DEFAULT_IMAGE_NAME, img);
		return bg;
	}

	public void loadWorld(WorldModel world, String filename)
	{
		open(filename);
	}

	public void draw()
	{}

    public static void main(String[] args)
    {
       PApplet.main("Main");
    }
}
