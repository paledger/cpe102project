// entities.py in Java
// Paula's parts

import java.lang.Math;
import java.random;

public class Data
{
	public static final int BLOB_RATE_SCALE = 4;
    public static final int BLOB_ANIMATION_RATE_SCALE = 50;
	public static final int BLOB_ANIMATION_MIN = 1;
	public static final int BLOB_ANIMATION_MAX = 3;

	public static final int ORE_CORRUPT_MIN = 20000;
	public static final int ORE_CORRUPT_MAX = 30000;

	public static final int QUAKE_STEPS = 10;
	public static final int QUAKE_DURATION = 1100;
	public static final int QUAKE_ANIMATION_RATE = 100;

	public static final int VEIN_SPAWN_DELAY = 500;
	public static final int VEIN_RATE_MIN = 8000;
	public static final int VEIN_RATE_MAX = 17000;
}

public Interface Entity
{
	String get_name();
    String set_position();
    String get_position();
    Image get_images();
    Image get_image();
    int get_rate();
    Image next_image();
    String entity_string();
}

public Interface Resource
{
	void set_resource_count(int n);
	int get_resource_count();
	int get_resource_limit();
}

public class Actionable
{
	public Actionable(ArrayList pending_actions)
	{
		List this.pending_actions = new ArrayList [];
	}

	protected get_pending_actions()
	{
		if (hasattr(this, "pending_actions"))
		{
			return this.pending_actions;
		}   //place where second note in hw3notes might be handy
		else
		{
			return [];
	    }
	}

	protected add_pending_action(Type action)
	{
		if hasattr(this, "pending_actions")
		{
			this.pending_actions.append(action);
		} 
	}

	protected remove_pending_action(Type action)
	{
		if hasattr(this, "pending_actions")
		{
			this.pending_actions.remove(action);
		} 
	}

	protected remove_pending_action(Type action)
	{
		if hasattr(this, "pending_actions")
		{
			this.pending_actions = [];
		} 
	}
}

