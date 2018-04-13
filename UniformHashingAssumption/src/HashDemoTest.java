import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Ang Li
 *
 */
public class HashDemoTest {
	@Test
	public void hash() throws Exception {

		HashDemo hashDemo = new HashDemo(100);

		assertEquals(0, hashDemo.getHashCounter());
		assertEquals(false, hashDemo.isBP());
		assertEquals(false, hashDemo.isCC());

		hashDemo.insert(22);
		assertEquals(1, hashDemo.getHashCounter());

		hashDemo.insert(22);
		assertEquals(true, hashDemo.isBP());
		assertEquals(false, hashDemo.isCC());

		for (int i = 0; i < 100; i++)
			hashDemo.insert(i);
		assertEquals(true, hashDemo.isCC());
	}
}
