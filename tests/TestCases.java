import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Before;

public class TestCases
{
	@Test
	public void testPointArgs()
	{
        Point pt = new Point(3, 4);
        assertEquals(pt.x(), 3);
        assertEquals(pt.y(), 4);
	}

/*	@Test
	public void testPoint_withinBounds()
	{
		Point pt = new Point()
	} */
}