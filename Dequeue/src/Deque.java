import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

	/** it is a generalization of both stack and queue.
	 * Add and remove from both at front or back of the data structure.
	 * I'll be using Linked list as data structure.
	 */
	
	private class Node {
		Item item;
		Node next;
		Node prev;
	}
	
	private Node head = null;
	private Node last = null;
	private int size = 0;
	
	private class ListIterator implements Iterator<Item> {
		// Implement a ListIterator
		private Node current = head;
		
		public boolean hasNext() {
			return current != null;
		}
		
		public Item next() {
			if (current == null) {
				throw new java.util.NoSuchElementException();
				}
			
			Item item = current.item;
			current = current.next;
			return item;
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
	}
	
	public Deque() {
		// construct an empty deque.
		head = last;
	}
	
	public boolean isEmpty() {
		// is the deque empty?
		return head == null && last == null;
	}
	
    public int size() {
    	// return the number of items on the deque
    	return size;
    }
    
    public void addFirst(Item item) {
    	// add the item to the front
    	if (item == null) {
    		throw new java.lang.IllegalArgumentException();
    	}
    	
    	if (isEmpty()) {
    		head = new Node();
    		head.item = item;
    		head.next = null;
    		head.prev = null;
    		last = head;
    	} else {   	
    		Node oldhead = head;
    		head = new Node();
    		head.item = item;
    		head.next = oldhead;
    		head.prev = null;
    	}
    	size++;
    }
    
    public void addLast(Item item) {
    	// add the item to the end
    	if (item == null) {
    		throw new java.lang.IllegalArgumentException();
    	}
    	
    	if (isEmpty()) {
    		last = new Node();
    		last.item = item;
    		last.next = null;
    		last.prev = null;
    		head = last;
    	} else {
    		Node oldlast = last;
    		last = new Node();
    		last.item = item;
    		last.next = null;
    		oldlast.next = last;
    		last.prev = oldlast;
    	}
    	size++;
    }
    
    public Item removeFirst() {
    	// remove and return the item from the front
    	if (isEmpty()) {
    		throw new java.util.NoSuchElementException();
    	}
    	
    	Item data = head.item;
    	head = head.next;
    	head.prev = null;
    	size--;
    	return data;
    }
    
    public Item removeLast() {
    	// remove and return the item from the end
    	if (isEmpty()) {
    		throw new java.util.NoSuchElementException();
    	}
    	
    	Item data = last.item;
    	last = last.prev;
    	last.next = null;
    	size--;
    	return data;
    }
    
    
    public Iterator<Item> iterator() {
    	// return an iterator over items in order from front to end
    	return new ListIterator();
    }
    
    public static void main(String[] args) {
    	// unit testing (optional)
    	Deque<String> deck = new Deque<String>();
    	deck.addLast("hello");
    	deck.addLast("can");
    	deck.addFirst("you");
    	deck.addLast("hear");
    	deck.addFirst("me");
    	for (String str:deck) {
    		System.out.println(str);
    	}
    	System.out.println("insert over, starting delete opn");
    	System.out.println(deck.removeFirst());
    	System.out.println(deck.removeLast());
    	System.out.println(deck.removeLast());
    	System.out.println("remaining elements in deque: ");
    	for (String str:deck) {
    		System.out.println(str);
    	}
    }

}
