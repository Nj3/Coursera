import edu.princeton.cs.algs4.StdIn;

public class Permutation { 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> rand_q = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String ip = StdIn.readString();
			rand_q.enqueue(ip);
		}
		for ( int i = 0; i < k; ++i ) {
			System.out.println(rand_q.dequeue());
		}
	}

}
