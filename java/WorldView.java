import java.lang.Math;
import java.lang.Object;
import processing.core.*;
import java.util.ArrayList;

// check types of arguments passed in

public class WorldView
{
   private Rect viewport;

   private int view_cols;
   private int view_rows;
   private PImage screen;
   private WorldModel world;
   private int tile_width;
   private int tile_height;
   private PImage mouseImg;

   private int numCols;
   private int numRows;

   private Point mousePt;


	public WorldView(int view_cols, int view_rows, PImage screen, WorldModel worldModel, 
		int tile_width, int tile_height, PImage mouseImg)  
	{ 
		viewport = new Rect(0, 0, view_cols, view_rows);

      this.view_cols = view_cols;
      this.view_rows = view_rows;
      this.screen = screen;
      this.world = worldModel;
      this.tile_width = tile_width;
      this.tile_height = tile_height;
      this.mouseImg = mouseImg;

      this.numCols = world.num_cols();
      this.numRows = world.num_rows();
      this.mousePt = new Point(0, 0);
	}

	public Point viewportToWorld(Point pt)
	{
		Point newPt = new Point(pt.x() - viewport.left(), pt.y() + viewport.top());
		return newPt;
	}

	public Point worldToViewport(Point pt)
	{
		Point newPt = new Point(pt.x() - viewport.left(), pt.y() - viewport.top());
      return newPt;
	}

   Utility helper = (int v, int low, int high) ->
      {
      	int max = Math.max(v, low);
      	int min = Math.min(high, max);
      	return min;
      };

	public Rect createShiftedViewport(int[] delta)
	{
		int newX = helper.clamp(viewport.left() + delta[0], 0,
			numCols - viewport.width());
		int newY = helper.clamp(viewport.top() + delta[1], 0,
			numRows - viewport.height());
		Rect newRect = new Rect(newX, newY, viewport.width(), viewport.height());
		return newRect;
	}

	public void drawBackground()
	{
		for(int y = 0; y < viewport.height(); y ++)
	   {
	   	for(int x = 0; x < viewport.width(); x ++)
	   	{
	   		Point w_pt = viewportToWorld(new Point(x, y));
	   		int[] realPos = {x*tile_width, y*tile_height};
	   		PImage img = world.getBackgroundImage(w_pt);
	   		screen.blit(img, realPos);
	   	}
	   }
	}


// FIGURE OUT WHAT THE HECK BLIT IS AND HOW TO DO IT IN JAVA
	public void drawEntities()
	{
		for(int i = 0; i < world.entities().size(); i ++)
		{
			Entity entity = world.entities().get(i);
			if(viewport.collidepoint(entity.getPosition()))
			{
				Point v_pt = worldToViewport(entity.getPosition());
				int[] realPos = {v_pt.x()*tile_width, v_pt.y()*tile_height};
				Actionable newEnt = (Actionable)entity;
				screen.blit(newEnt.getImage(), realPos);
			}
		}
	}

	public void drawViewport()
	{
		drawBackground();
		drawEntities();
	}

	public void updateView(int[] viewDelta, PImage mouseImg2)
	{
      viewport = createShiftedViewport(viewDelta);
      this.mouseImg = mouseImg2;
      drawViewport();
      //HOW TO DO PYGAME.DISPLAY.UPDATE() IN JAVA
      //mouseMove(mousePt);
	}

/*	public void updateViewTiles(Point[] tiles)
	{
		ArrayList<Rect> rects = new ArrayList<Rect>();
		for(int i = 0; i < tiles.length; i ++)
		{
			Point tile = tiles[i];
			if(viewport.collidepoint(tile))
			{
				Point v_pt = worldToViewport(tile);
				PImage img = getTileImage(v_pt);
				rects.add(updateTile(v_pt, img));
				if (mousePt.x() == v_pt.x() && 
					mousePt.y() == v_pt.y())
				{
					rects.add(updateMouseCursor());
				}
			}
		}
	   // EQUIVALENT OF PYGAME.DISPLAY.UPDATE()
	}*/

	public Rect updateTile(Point view_tile_pt, PImage surface)
	{
		int abs_x = view_tile_pt.x()*tile_width;
		int abs_y = view_tile_pt.y()*tile_height;

		//seems to be a mouse function?????????
		//image(surface, abs_x, abs_y);
		return new Rect(abs_x, abs_y, tile_width, tile_height);
	}

/*	public PImage getTileImage(Point view_tile_pt)
	{
		Point pt = viewportToWorld(view_tile_pt);
		PImage bgnd = world.getBackgroundImage(pt);
		Entity occupant = world.getTileOccupant(pt);
		if(occupant != null)
		{
			PImage img = pygame.Surface((tile_width, tile_height));// SURFACE  IN PYGAME
			int[] pos = {0, 0};
			img.blit(bgnd, pos);
			img.blit(occupant.getImage(), pos);
			return img;
		}
		else
		{
			return bgnd;
		}
	}*/


/* MOUSE HOVERING STUFF THAT IS UNNECCESSARY BUT WE CAN CHECK TO SEE HOW TO DO THIS
	public PImage createMouseSurface(boolean occupied)
	{
		PImage surface = pygame.Surface((tile_width, tile_height));
		surface.set_alpha(MOUSE_HOVER_ALPHA);
		int color = MOUSE_HOVER_EMPTY_COLOR;
		if(occupied)
		{
			color = MOUSE_HOVER_OCC_COLOR;
		}
		surface.fill(color);
		if(mouseImg)
		{
			surface.blit(mouseImg, pos);
		}
		return surface;
	}


	public updateMouseCursor()
	{
		boolean here = createMouseSurface(viewportToWorld(mousePt)).isOccupied(world);
		return updateTile(mousePt, )
	}

	public handle
*/

	
}

