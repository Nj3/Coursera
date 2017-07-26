import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @author Nj3
 * A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items in the data structure.
 * Choice of data structure - Array.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
//public class RandomizedQueue<Item> {
	/**
	 * LIST OF METHODS:
	 * public boolean isEmpty()                 // is the queue empty?
	 * public int size()                        // return the number of items on the queue
	 * public void enqueue(Item item)           // add the item
	 * public Item dequeue()                    // remove and return a random item
	 * public Item sample()                     // return (but do not remove) a random item
	 * public Iterator<Item> iterator()         // return an independent iterator over items in random order
	 */
	
	
	private int size = 0;
	private int last = 0;
	private Item[] rq;
	
	private class ListIterator implements Iterator<Item> {
		// Implement a ListIterator WIP
		private int initial_size = size;
		Item[] temp_array = (Item[]) new Object[initial_size];
		private int start = 0;
		
		public ListIterator() {
			for ( int i = 0; i < initial_size; ++i ) {
				temp_array[i] = rq[i];
			}
			StdRandom.shuffle(temp_array);
		}
		
		public boolean hasNext() {
			return start < initial_size;
		}
		
		public Item next() {
			if ( !hasNext() ) {
				throw new java.util.NoSuchElementException();
				}
			
			Item item = temp_array[start++];			
			return item;
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
	}
	
	public Iterator<Item> iterator() {
    	// return an iterator over items in order from front to end
    	return new ListIterator();
    }
	
	
	
	public RandomizedQueue() {
		// construct an empty randomized queue
		rq = (Item[]) new Object[2];
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}
	
	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		int j = 0;
		for ( int i = 0; i < rq.length; ++i ) {
			if ( rq[i] != null) copy[j++] = rq[i];
			if ( j == size ) break;
		}
		rq = copy;
	}
	
	private void display() {
		for (int i=0; i<rq.length; i++) {
			System.out.print(rq[i]);
		}
	}
	/*
	private void restruct(int ind_st) {
		for (int i = ind_st; i<size; ++i) {
			if ( rq[i+1] != null) rq[i] = rq[i+1];
		}
	}*/
	
	public void enqueue(Item item) {
		if (size == rq.length) {
			resize(2*size);
		}
		rq[last++] = item;
		size++;
	}
	
	public Item dequeue() {
		//StdRandom.shuffle(rq);
		//Item item = rq[last];
		//rq[last--] = null; // removes loitering
		int index_to_rm = StdRandom.uniform(size);
		System.out.println("index chosen at random is" + index_to_rm);
		Item item = rq[index_to_rm];
		rq[index_to_rm] = rq[--last]; // when the same index is picked again, it will pick null. SO to avoid that i'm placing last number to this index.
		rq[last] = null; //removes loitering
		if ( item != null) size--; // It covers the scenario when index_to_rm has the same number chosen randomly before an array is resized.
		if (size > 0 && size == rq.length/4) {
			resize(rq.length/2);
		}
		return item;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomizedQueue<String> rand_q = new RandomizedQueue<String>();
		int i= 0;
		while (i < 5) {
			String ip = StdIn.readString();
			rand_q.enqueue(ip);
			++i;
		}
		System.out.println("Initial array");
		rand_q.display();
		System.out.println();
		System.out.println(rand_q.dequeue());
		rand_q.display();
		System.out.println(rand_q.dequeue());
		rand_q.display();/*
		System.out.println(rand_q.dequeue());
		rand_q.display();
		System.out.println(rand_q.dequeue());
		rand_q.display();
		System.out.println(rand_q.dequeue());
		rand_q.display();*/
		System.out.println("Iterator check");
		for (String s:rand_q) {
			System.out.println(s);
			System.out.println("Inside foreach loop");
			for(String sin:rand_q) {
				System.out.println(sin);
			}
			System.out.println();
		}
	}

}
