import java.util.Stack;

public class shell_sort {
	
	private boolean less(Integer v, Integer w) {
		return v.compareTo(w) < 0;
	}
	
	private void exch(int[] a, int i, int j) {
		int swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	private void sort(int[] arr) {
		int N = arr.length;
		//Maintain a stack to create the increments in which it will be h-sorted
		Stack<Integer> inc = new Stack<Integer>();
		int x = 0;
		while ( (3*x + 1) < N) {
			inc.push(3*x + 1);
			++x;
		}
		
		while (!inc.empty()) {
			int h = inc.pop();
		
			for (int i=0; i<N && i+h < N; i++) {
				for (int j=i+h; j-h>=0; j-=h) {
					if ( less(arr[j], arr[j-h]) ) exch(arr, j, j-h);
					else break;
				}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] ip = new int[] {1, 5, -1, 0, 2, 22, 18, 1, -500, 30, 102, 15002, 3, -11};
		shell_sort res = new shell_sort();
		res.sort(ip);
		for (int o: ip) {
			System.out.println(o);
		}
	}

}
