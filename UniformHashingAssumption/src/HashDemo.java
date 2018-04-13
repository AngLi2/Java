/**
 * @author Ang Li
 *
 */
class HashDemo {
	private int[] hashTableDemoList;
	private int size;
	private int emptyNum;
	private int hashCounter = 0;

	public HashDemo(int m) {
		this.size = m;
		this.emptyNum = m;
		hashTableDemoList = new int[m];
		for (int i = 0; i < m; i++)
			hashTableDemoList[i] = 0;
	}

	public int getHashCounter() {
		return hashCounter;
	}

	public void insert(int currentKey) {
		hashCounter++;
		int inputLocation = hash(currentKey, size);
		if (hashTableDemoList[inputLocation] == 0)
			emptyNum--;
		hashTableDemoList[inputLocation]++;
	}

	public boolean isBP() {
		for (int i : hashTableDemoList)
			if (i >= 2)
				return true;
		return false;
	}

	public boolean isCC() {
		return emptyNum == 0;
	}

	private static int hash(int key, int m) {
		return (key & 0x7fffffff) % m;
	}
}