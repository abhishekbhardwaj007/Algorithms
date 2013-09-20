import java.util.Iterator;
import java.util.Scanner;


public class Subset {

	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		int count = 0;
		String line = null;

		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		
		while (!StdIn.isEmpty())
		{
			line = StdIn.readString();
			//System.out.println(line);
			rq.enqueue(line);
		}


		Iterator<String> it = rq.iterator();
		while((it.hasNext()) && (count < k))
		{
			System.out.println(it.next());
			count++;
		} 
	}
}
