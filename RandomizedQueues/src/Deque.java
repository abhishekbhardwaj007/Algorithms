import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {
	
	private int N;
	private Node Head;
	private Node Tail;
	
	private class Node {
		Item item;
		Node next;
		Node prev;
		
		public Node(Item it) {
			this.next = null;
			this.prev = null;
			this.item = it;
		}
	}
	
	public Deque() {
		this.N = 0;
		this.Head = null;
		this.Tail = null;
	}
	
	public boolean isEmpty() {
		return (this.N == 0);
	}
	
	public int size() {
		return this.N;
	}
	
	public void addFirst(Item item) { 
		
		if (item == null)
		{
			throw new java.lang.NullPointerException();
		}
		
		Node node = new Node(item);
		
		if ((this.Head == null) && (this.Tail == null)) {
			this.Head = node;
			this.Tail = node;
		}
		else
		{
			node.next = this.Head;
			this.Head.prev = node;
			
			this.Head = node;
		}
		
		this.N++;
	}
	
	public void addLast(Item item) { 
		
		if (item == null)
		{
			throw new java.lang.NullPointerException();
		}
		
		Node node = new Node(item);
		
		if ((this.Head == null) && (this.Tail == null)) {
			this.Head = node;
			this.Tail = node;
		}
		else
		{
			this.Tail.next = node;
			node.prev = this.Tail;
			
			this.Tail = node;
		}
		
		this.N++;
	}
	
	public Item removeFirst() {
		
		Item it;
		
		if ((this.Head == null) && (this.Tail == null)) {
			throw new java.util.NoSuchElementException();
		}
		
		if (this.Head == this.Tail)
		{
			it = this.Head.item;
			
			this.Head = null;
			this.Tail = null;
		}
		else
		{
			it = this.Head.item;
			
			this.Head = this.Head.next;
			
			//
			// To prevent loitering
			//
			this.Head.prev = null;
		}
		
		this.N--;
		
		return it;
	}
	
	public Item removeLast() {
		
		Item it;
		
		if ((this.Head == null) && (this.Tail == null)) {
			throw new java.util.NoSuchElementException();
		}
		
		if (this.Head == this.Tail)
		{
			it = this.Tail.item;
			
			this.Head = null;
			this.Tail = null;
		}
		else
		{
			it = this.Tail.item;
			
			this.Tail = this.Tail.prev;
			
			//
			// To prevent loitering
			//
			this.Tail.next = null;
		}
		
		this.N--;
		
		return it;
	}
	
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	private class DequeIterator implements Iterator<Item> {
		
		Node current;
		
		public DequeIterator() {
			current = Head;
		}
		
		public boolean hasNext() {
			return current != null;
		}
		
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
		public Item next() {
			
			if (current == null)
			{
				throw new java.util.NoSuchElementException();
			}
			
			Item it = current.item;
			
			current = current.next;
			return it;
		}
	}
	
	/*
	public static void main(String[] Args)
	{
		Deque<Integer> dq = new Deque<Integer>();
		
		for (int i = 0 ; i < 100; i++)
		{
			dq.addFirst(i);
		}
		
		
		for (int i = 0 ; i < 100; i++)
		{
			System.out.println(dq.removeLast());
		}
		
		System.out.println(dq.isEmpty());
		
		dq.addFirst(1);
		dq.addLast(2);
		System.out.println(dq.removeFirst());
		
		Iterator<Integer> it = dq.iterator();
		
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
		
	} */
	
}
