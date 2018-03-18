public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int size;

	private class Node {
		Node previous;
		Node next;
		Item item;
	}

	public Deque() {
		first = null;
		last = null;
		size = 0;
	}

	public boolean isEmpty() {
		return 0 == size;
	}

	public int size() {
		return size;
		// return the number of items on the deque
	}

	public void addFirst(Item item) {
		if (item == null)
			throw new java.lang.IllegalArgumentException();
		Node old = first;
		first = new Node();
		first.item = item;
		first.previous = null;
		if (isEmpty()) {
			first.next = null;
			last = first;
		} else {
			first.next = old;
			first.next.previous = first;
		}
		size++;
		// add the item to the front
	}

	public void addLast(Item item) {
		if (item == null)
			throw new java.lang.IllegalArgumentException();
        Node old = last;  
        last = new Node();  
        last.item = item;  
        last.next = null;  
        if (isEmpty()){  
            last.previous = null;  
            first = last;  
        }else{  
            last.previous = old;  
            last.previous.next = last;  
        }  
        size++;  
	}

	public Item removeFirst() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = first.item;
		first = first.next;
		size--;
		if (isEmpty()) {
			last = null;
			first = null;
		} else {
			first.previous = null;
		}
		return item;
	}

	public Item removeLast() {
		if (isEmpty())
			throw new java.util.NoSuchElementException();
		Item item = last.item;
		last = last.previous;
		size--;
		if (isEmpty()) {
			last = null;
			first = null;
		} else {
			last.next = null;
		}
		return item;
	}

	public java.util.Iterator<Item> iterator() {
		return new ListIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ListIterator implements java.util.Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<Integer>();
		deque.addFirst(1);
		deque.addFirst(2);
		deque.addFirst(3);
		System.out.println(deque.removeFirst());
		System.out.println(deque.removeLast());
		System.out.println(deque.removeFirst());
		System.out.println(deque.removeLast());
	}

}
