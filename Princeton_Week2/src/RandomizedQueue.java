import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] q; // queue elements
	private int N = 0; // number of elements on queue

	// cast needed since no generic array creation in Java
	public RandomizedQueue() {
		q = (Item[]) new Object[2];
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	// resize the underlying array
	private void resize(int max) {
		assert max >= N;
		Item[] temp = (Item[]) new Object[max];
		for (int i = 0; i < N; i++) {
			temp[i] = q[i];
		}
		q = temp;
	}

	public void enqueue(Item item) {
		if (item == null) {
			throw new java.lang.IllegalArgumentException();
		}
		// double size of array if necessary and recopy to front of array
		if (N == q.length)
			resize(2 * q.length); // double size of array if necessary
		q[N] = item; // add item
		N++;
	}

	public Item dequeue() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		int index = StdRandom.uniform(N);
		Item item = q[index];
		if (index != N - 1) {
			q[index] = q[N - 1];
		}
		q[N - 1] = null; // to avoid loitering
		N--;

		if (N > 0 && N == q.length / 4)
			resize(q.length / 2);
		return item;
	}

	// return (but do not delete) a random item
	public Item sample() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		int index = (StdRandom.uniform(N));
		return q[index];
	}

	public java.util.Iterator<Item> iterator() {
		return new ArrayIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ArrayIterator implements java.util.Iterator<Item> {
		private Item[] tempItem = (Item[]) new Object[q.length];

		private int tempN = N;

		public boolean hasNext() {
			return tempN != 0;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public ArrayIterator() {
			for (int j = 0; j < q.length; j++) {
				tempItem[j] = q[j];
			}
		}

		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			int index = (StdRandom.uniform(tempN));
			Item item = tempItem[index];
			if (index != tempN - 1) {
				tempItem[index] = tempItem[tempN - 1];
			}
			tempItem[tempN - 1] = null; // to avoid loitering
			tempN--;
			return item;
		}
	}

	public static void main(String[] args) {
		RandomizedQueue<String> q = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-"))
				q.enqueue(item);
			else if (!q.isEmpty())
				StdOut.print(q.dequeue() + " ");
		}
		StdOut.println("(" + q.size() + " left on queue)");
	}
}