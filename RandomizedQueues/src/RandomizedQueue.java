import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int N;
	private int Len;

	private Item[] it;

	public RandomizedQueue()  {
		this.Len = 1;
		it = (Item[]) (new Object[1]);
	}

	public boolean isEmpty() {
		return (this.N == 0);
	}

	public int size()  {
		return this.N;
	}

	public void enqueue(Item item) {

		if (item == null)
		{
			throw new java.lang.NullPointerException();
		}

		if (this.N == this.Len) {
			copy(2 * this.Len);

			this.Len = 2 * this.Len;
		}

		it[this.N++] = item;
	}

	public Item dequeue() {

		if (this.N <= 0) {
			throw new java.util.NoSuchElementException();
		}

		Item item = it[--this.N];

		//
		// To prevent loitering
		//
		it[this.N] = null;

		if ((this.N > 0) && (this.N == (this.Len / 4))) {
			copy(this.Len / 2);

			this.Len = this.Len / 2;
		}

		return item;
	}

	public Item sample() {

		if (this.N <= 0) {
			throw new java.util.NoSuchElementException();
		}

		return it[StdRandom.uniform(this.N)];
	}

	private void copy(int capacity) {

		Item[] newitem = (Item[]) new Object[capacity];

		for(int i = 0; i < this.N; i++) {
			newitem[i] = it[i];
		}

		it = newitem;
	}

	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private class RandomizedQueueIterator implements Iterator<Item> {

		int currentIndex;

		public RandomizedQueueIterator() {
			this.currentIndex = 0;
		}

		public boolean hasNext() {
			return (this.currentIndex < N);
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {

			if ((currentIndex < 0) || (currentIndex >= N))
			{
				throw new java.util.NoSuchElementException();
			}

			Item item = it[currentIndex];

			currentIndex++;

			return item;
		}
	}

	public static void main(String[] Args)
	{
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

		for (int i = 0 ; i < 100; i++)
		{
			rq.enqueue(i);
		}


		for (int i = 0 ; i < 100; i++)
		{
			System.out.println(rq.dequeue());
		}

		System.out.println(rq.isEmpty());

		rq.enqueue(1);
		rq.enqueue(2);
		System.out.println(rq.dequeue());

		Iterator<Integer> it = rq.iterator();

		while(it.hasNext())
		{
			System.out.println(it.next());
		}

	}


}
