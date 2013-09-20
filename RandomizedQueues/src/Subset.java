import java.util.Iterator;


public class Subset {
	
	
	
	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		int N = 0;
		int count = 0;
		String s = null;

		System.out.println(k);
		
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		while ((s = StdIn.readString()) != null)
		{
			rq.enqueue(s);
			N++;
		}
		
	/*	rq.enqueue("A");
		rq.enqueue("B");
		rq.enqueue("C");
		rq.enqueue("D");
		rq.enqueue("E");
		rq.enqueue("F");
		rq.enqueue("G");
		rq.enqueue("H");
		rq.enqueue("I");
		N = 9; */
		
		Iterator<String> it = rq.iterator();

		
		while((it.hasNext()) && (count < k))
		{
			System.out.println(it.next());
			count++;
		} 
	}
}
