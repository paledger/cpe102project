import processing.core.*;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

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

	public WorldView view;
	public WorldModel world;
	public HashMap<String, LinkedList<String>> iStore;


	public void setup()
	{
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		Scanner scanner = new Scanner("imagelist");
		iStore = ImageStore.processLines(scanner);

		int numCols = SCREEN_WIDTH/TILE_WIDTH*WORLD_WIDTH_SCALE;
		int numRows = SCREEN_HEIGHT/TILE_HEIGHT*WORLD_HEIGHT_SCALE;
		Background defaultBackground = createDefaultBackground(ImageStore.getImages(iStore, ImageStore.DEFAULT_IMAGE_NAME));
		world = new WorldModel(numRows, numCols, defaultBackground);
		view = new WorldView(SCREEN_WIDTH/TILE_WIDTH, SCREEN_HEIGHT/TILE_HEIGHT, 
			world, TILE_WIDTH, TILE_HEIGHT);
	}

	public Background createDefaultBackground(LinkedList<String> img)
	{
		LinkedList<PImage> imgs = ImageStore.makeImageList(iStore.get("background_default"));
		Background bg = new Background(DEFAULT_IMAGE_NAME, img);
		return bg;
	}
	public void draw()
	{
		randomSeed();
		view.updateView();
	}

    public static void main(String[] args)
    {
       PApplet.main("Main");
    }
}
