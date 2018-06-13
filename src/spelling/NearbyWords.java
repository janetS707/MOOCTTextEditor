/**
 * 
 */
package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class NearbyWords implements SpellingSuggest {
	// THRESHOLD to determine how many words to look through when looking
	// for spelling suggestions (stops prohibitively long searching)
	// For use in the Optional Optimization in Part 2.
	private static final int THRESHOLD = 1000; 

	Dictionary dict;

	public NearbyWords (Dictionary dict) 
	{
		this.dict = dict;
	}

	/** Return the list of Strings that are one modification away
	 * from the input string.  
	 * @param s The original String
	 * @param wordsOnly controls whether to return only words or any String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s, boolean wordsOnly )  {
		   List<String> retList = new ArrayList<String>();
		   insertions(s, retList, wordsOnly);
		   substitution(s, retList, wordsOnly);
		   deletions(s, retList, wordsOnly);
		   return retList;
	}

	
	/** Add to the currentList Strings that are one character mutation away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// use StringBuffer for an easy interface to permuting the 
				// letters in the String
				StringBuffer sb = new StringBuffer(s);
				sb.setCharAt(index, (char)charCode);
				//System.out.println("word from substitutions: " + sb.toString());
				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					//System.out.println("add word from substitutions: " + sb.toString());
					currentList.add(sb.toString());
				}
			}
		}
	}
	
	/** Add to the currentList Strings that are one character insertion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index <= s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// use StringBuffer for an easy interface to permuting the 
				// letters in the String
				StringBuffer sb = new StringBuffer(s);
				sb.insert(index, (char)charCode);
				//System.out.println("word from insertions: " + sb.toString());
				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				if(!currentList.contains(sb.toString()) && 
						(!wordsOnly||dict.isWord(sb.toString())) &&
						!s.equals(sb.toString())) {
					//System.out.println("add word from insertions: " + sb.toString());
					currentList.add(sb.toString());
				}
			}
		} 
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly ) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			// use StringBuffer for an easy interface to permuting the 
			// letters in the String
			StringBuffer sb = new StringBuffer(s);
			sb.delete(index, index+1);
			//System.out.println("word from deletions: " + sb.toString());
			// if the item isn't in the list, isn't the original string, and
			// (if wordsOnly is true) is a real word, add to the list
			if(!currentList.contains(sb.toString()) && 
					(!wordsOnly||dict.isWord(sb.toString())) &&
					!s.equals(sb.toString())) {
				//System.out.println("add word from deletions: " + sb.toString());
				currentList.add(sb.toString());
			}
		} 
	}

	/** Add to the currentList Strings that are one mutation (insert, delete, substitute)
	 * away from the input string.  
	 * @param word The misspelled word
	 * @param numSuggestions is the maximum number of suggestions to return 
	 * @return the list of spelling suggestions
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) {

		//Create a queue to hold words to explore
		LinkedList<String> queue = new LinkedList<String>();     
		
		//Create a visited set to avoid looking at the same String repeatedly
		HashSet<String> visited = new HashSet<String>();   
		
		//Create list of real words to return when finished
		LinkedList<String> retList = new LinkedList<String>();   
		 
		//Add the initial word to the queue and visited 
		queue.add(word);
		visited.add(word);
		
		 Dictionary d = new DictionaryHashSet();
		 DictionaryLoader.loadDictionary(d, "data/dict.txt");
		
		// System.out.println("numSuggestions: " + numSuggestions);
		// System.out.println("word: " + word);
		
		//while the queue has elements and we need more suggestions 
		while(queue.size() > 0 && retList.size() < numSuggestions) {
			//System.out.println("return list size: " + retList.size());
			//remove the word from the start of the queue and assign to curr 
			String curr = queue.removeFirst();
			//System.out.println("curr = " + curr);
			
			//get a list of neighbors (strings one mutation away from curr) 
			
			List<String> neighbors = distanceOne(curr, true);
			//System.out.println("neighbors = " + neighbors.toString());
			
			//for each n in the list of neighbors 
			for(int k = 0; k < neighbors.size(); k++){
			//for(String n : neighbors) {
				String n = neighbors.get(k);
				//System.out.println("n="+ n);
				
				//if n is not visited 
				if(!visited.contains(n)) {
					
					//add n to the visited set 
					visited.add(n);
					//System.out.println("add this to visited = " + n);
					
					//add n to the back of the queue 
					queue.addLast(n);
					//System.out.println("queue = " + queue.toString());
					
					//if n is a word in the dictionary
					
					if (dict.isWord(n)) {
										
						//add n to the list of words to return 
						if(retList.size() < numSuggestions) {
							retList.add(n);
							//System.out.println("*************retList = " + retList.toString());
						}
						else if(retList.size() == numSuggestions) {
							//System.out.println("have enough suggestions -- get out of this loop!!!!!");
							k = neighbors.size();
						}
					}
				}
			}
		}
		//return the list of real words
		return retList;

	}	

   public static void main(String[] args) {
	   //basic testing code to get started
	   String word = "fox";
	   // Pass NearbyWords any Dictionary implementation you prefer
	   Dictionary d = new DictionaryHashSet();
	   DictionaryLoader.loadDictionary(d, "data/dict.txt");
	   NearbyWords w = new NearbyWords(d);
	   List<String> l = w.distanceOne(word, true);
	   System.out.println("One away word Strings for for \""+word+"\" are:");
	   System.out.println(l+"\n");

	   word = "word";
	   List<String> suggest = w.suggestions(word, 4);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(suggest);
	 
   }

}
