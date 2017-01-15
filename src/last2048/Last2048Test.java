package last2048;
import last2048.Last2048;
import last2048.Last2048.Tuile;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Last2048Test
	{
		public Tuile TestTuile = new Tuile();

		@Before
		public void setUp() throws Exception
		{
		}

		@After
		public void tearDown() throws Exception
		{
		}

		@Test
		public void testpositionnementTuile()
		{
			assertEquals("positionnementTuile", 120,Last2048.positionnementTuile(1));;
		}
		
		@Test
		public void testgetTailleFont()
		{
			TestTuile.valeur =8;
			assertEquals("getTailleFont", 36,Last2048.getTailleFont(TestTuile));;
			TestTuile.valeur =128;
			assertEquals("getTailleFont", 32,Last2048.getTailleFont(TestTuile));;
			TestTuile.valeur =1024;
			assertEquals("getTailleFont", 24,Last2048.getTailleFont(TestTuile));;
		}
		
		
//		@Test
//		public void testpositionnementTuile()
//		{
//			assertEquals("positionnementTuile", 120,Last2048.positionnementTuile(1));;
//		}

	}
