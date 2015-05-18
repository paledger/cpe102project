import processing.core.*;

public class Main
    extends Papplet
{
	public static boolean RUN_AFTER_LOAD = True

	public static String IMAGE_LIST_FILE_NAME = 'imagelist'
	public static String WORLD_FILE = 'gaia.sav'

	public final static int WORLD_WIDTH_SCALE = 2
	public final static int WORLD_HEIGHT_SCALE = 2

	public final static int SCREEN_WIDTH = 640
	public final static int SCREEN_HEIGHT = 480
	public final static int TILE_WIDTH = 32
	public final static int TILE_HEIGHT = 32

	public PImage createDefaultBackground(PImage img)
	{
		Background bg = new Background(DEFAULT_IMAGE_NAME, img);
		return bg;
	}

	public void loadWorld(WorldModel world, File filename)
	{
		open()
	}

    public static void main(String[] args)
    {
       PApplet.main("Main");
    }
}
