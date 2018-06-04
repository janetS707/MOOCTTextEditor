package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    private Boolean added;
    private LinkedList<TrieNode> toDoList;
	private LinkedList<TrieNode> doneList;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		size = 0;
	}
	
	
	/** Insert a word into the trie.
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		TrieNode wordNode = getWord(word, "A");
	    	
	    if (added) //this word got added into the dictionary
	    {
	    	size++;
	    	return true;
	    }
	    
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String word) 
	{		
		TrieNode wordNode = getWord(word, "F");
		
    	if (wordNode != null) {
    		return true;
    	}
    	
	    return false;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {   
    	//Create a list of completions to return (initially empty)
    	List<String> predictions = new LinkedList<String>();
    	TrieNode startingNode = null;
    	
    	if (prefix != "") {
    		// Find the stem in the trie.  
    		startingNode = getWord(prefix, "P");
        	
        	//If the stem does not appear in the trie, return an empty list, 
        	if (startingNode == null){ //not found
        		return predictions;
        	}
    	}
        else{
        		startingNode = root;
        	}
    	
    	//Perform a breadth first search to generate completions...
	   	//Create a queue (LinkedList) and add the node that completes the stem to the back of the list.
    	toDoList = new LinkedList<TrieNode>();
    	toDoList.addLast(startingNode);
    	doneList = new LinkedList<TrieNode>();
	   	 
	   	//Look for predictions
    	traverse(prefix, numCompletions, predictions);
     	
    	 // Return the list of completions
    	return predictions;
     }

     
     /** Do a level traversal from this node down */
  	private List<String> traverse(String prefix, int numCompletions, List<String> predictions )
  	{  		
  		//While the queue is not empty and you don't have enough completions:
  		if (predictions.size() < numCompletions && toDoList.size() > 0) 
  		{	  		
  			//remove the first Node from the to do queue
  			TrieNode toDoNode = (TrieNode) toDoList.removeFirst();
  	  		
  		   	//If it is a word, add it to the completions list
  			if (toDoNode.endsWord())
  			{ 
  				predictions.add(toDoNode.getText());
  			}
  			
  		   	
  			//Add all of its child nodes to the back of the to do queue
	  		for (Character c : toDoNode.getValidNextCharacters()) 
	  		{
	  			TrieNode child = toDoNode.getChild(c);
	  			toDoList.addLast(child);
	  		}
	  		
	  		doneList.addLast(toDoNode); //add the current node to the done list
			
			traverse(prefix, numCompletions, predictions);
  		}
  		return predictions;
  	}
     
     private TrieNode getWord(String word, String searchType) {
	   added = false;
	   
	   if (word == "") {
		   return null;
	   }
	   
	   String lword = word.toLowerCase();
		TrieNode current = root;
	    char[] cArray = lword.toCharArray();
	    int length = cArray.length;
	   
	    
	    for (int i=0; i < length; i++) {
	    	char c = cArray[i];
	    	
	    	TrieNode nextNode = current.getChild(c);
	    	
	    	if (nextNode == null) //this char is not in this dictionary
	    	{
	    		if (searchType == "A") 
	    		{
	    			nextNode = current.insert(c);
	    			
	    			if (nextNode.getText().equalsIgnoreCase(lword))
	    		    {
	    				added = true;
	    				nextNode.setEndsWord(true);
	    		   		return nextNode;
	    		    }
	    		}
	    		else 
	    		{
		    	   	return null;	
	    		}
	    	}
	    	else if(nextNode.getText().equalsIgnoreCase(lword)) //subset word found/added
		    {
	    		if (!nextNode.endsWord()) 
	    		{
	    			if (searchType == "A") 
	    			{
		    			added = true;
		    			nextNode.setEndsWord(true);
	    			}
	    			else if (searchType == "P")
	    			{
	    				return nextNode;
	    			}
	    		}
		   		return nextNode;
		    }
    		current = nextNode;
	    }
	    
	    if (current.getText().equalsIgnoreCase(lword))
	    {
	   		return current;
	    }
	    return null;
   }
     
 	// For debugging
 	public void printTree(String name)
 	{
 		System.out.println(name);
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		
 		if (curr == null) {
 			return;
 		}
 		
 		if (curr.endsWord()) {
 			System.out.println(curr.getText());
 		}
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
}