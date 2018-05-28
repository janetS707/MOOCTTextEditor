package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/**		C O N S T R U C T O R
	 *  Create a new empty LinkedList */
	public MyLinkedList() {
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**  	 A D D  E L E M E N T
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		this.add(size(), element);
		return true;
	}
	
	/**		A D D  E L E M E N T @ I N D E X
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{		
		if(element == null) {
			throw new NullPointerException("null is not a valid value");
		}
		else {
			LLNode<E> nodeAtIndex = null;
			if(index == size()) {
				nodeAtIndex = tail;
			}
			else {
				nodeAtIndex = getNode(index);
			}
			//Create the new node and wire up it's pointers
			LLNode<E> newNode = new LLNode<E>(element, nodeAtIndex.prev, nodeAtIndex);
			
			//Update previous node pointer to point to new node
			LLNode<E> prevNode = nodeAtIndex.prev;
			prevNode.next = newNode;
			
			//Update prev pointer of node after new node to point to the new node
			nodeAtIndex.prev = newNode;
			
			//Modify the size
			this.size += 1;
		}
	}

	/**		G E T   E L E M E N T
	 * * Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. 
	 * */
	public E get(int index) 
	{
		LLNode<E> node = getNode(index);
		return (E) node.getData();
	}


	/** 	G E T   S I Z E
	 * Return the size of the list */
	public int size() 
	{
		return this.size;
	}

	/** 	R E M O V E   E L E M E N T
	 * Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		LLNode<E> nodeAtIndex = getNode(index);
		LLNode<E> prevNode = nodeAtIndex.prev;
		LLNode<E> nextNode = nodeAtIndex.next;
		
		prevNode.next = nextNode;
		nextNode.prev = prevNode;
		this.size = size() - 1;
		return (E) nodeAtIndex.data;
	}

	/**		S E T   V A L U E   @   I N D E X
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if(element == null) {
			throw new NullPointerException("null is not a valid value");
		}
		else {
			LLNode<E> nodeAtIndex = getNode(index);
			E oldValue = nodeAtIndex.getData();
			nodeAtIndex.setData(element);
			return oldValue;
		}
	}   
	
	/**		G E T   N O D E
	 * 
	 * @param index
	 * @return LLNode
	 */
	private LLNode<E> getNode(int index){
		if(index < 0 || index > size() - 1) {
			throw new IndexOutOfBoundsException("index is not valid");
		}
		else 
		{
			LLNode<E> currentNode = this.head;
			for(int k=0; k <= size(); k++) {
				currentNode = currentNode.next;
				if (index == k) {
					k = size();
				}	
			}	
			return currentNode;
		}
	}
	
	/**		P R I N T   T H E   L I S T
	 * 
	 */
	private void printList() {
		System.out.println("size of the list is: " + size());
		LLNode<E> currentNode = head;
		for(int k=0; k <= size() - 1; k++) {
			currentNode = currentNode.next;
			System.out.println("Node # " + k + " = " + currentNode.getData());	
			}
	}
}

/** 	T H E   N O D E   C L A S S
 * 
 * @author Janet
 *
 * @param <E>
 */
class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e) 
	{
		setData(e);
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e, LLNode<E> prevNode, LLNode<E> nextNode) 
	{
		this.data = e;
		this.prev = prevNode;
		this.next = nextNode;
	}
	
	public E getData() {
		return this.data;
	}
	
	public void setData(E e) {
		this.data = e;
	}
}
