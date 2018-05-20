package document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/** A class for timing the EfficientDocument and BasicDocument classes
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 */

public class DocumentBenchmarking {

	
	public static void main(String [] args) {

	    // Run each test more than once to get bigger numbers and less noise.
	    // You can try playing around with this number.
	    int trials = 100;

	    // The text to test on
	    String textfile = "data/warAndPeace.txt";
		
	    // The amount of characters to increment each step
	    // You can play around with this
		int increment = 20000;

		// The number of steps to run.  
		// You can play around with this.
		int numSteps = 20;
		
		// THe number of characters to start with. 
		// You can play around with this.
		int start = 50000;
		
		// TODO: Fill in the rest of this method so that it runs two loops
		// and prints out timing results as described in the assignment 
		// instructions and following the pseudocode below.
		
		System.out.println("NumChars" + "\t" + "BasicTime" + "\t" + "EfficientTime"); 
		for (int numToCheck = start; numToCheck < numSteps*increment + start; 
				numToCheck += increment)
		{
			// numToCheck holds the number of characters that you should read from the 
			// file to create both a BasicDocument and an EfficientDocument.  
			
			String chars = getStringFromFile(textfile, numToCheck);
			 
			double totalTime = 0;
			long startTime = 0;
			long endTime = 0;
			double basicDocTime = 0;
			double efficientDocTime = 0;
			
			//Get times for basic document:
			startTime=System.nanoTime();
			for (int i = 0; i<trials; i++) {
				document.BasicDocument basicDoc = new document.BasicDocument(chars);
				Double basicDocScore = basicDoc.getFleschScore();		
			}
			endTime=System.nanoTime();
			basicDocTime = (endTime - startTime)/100000000.0;
			
			//Get times for efficient document:
			startTime=System.nanoTime();
			for (int i = 0; i<trials; i++) {
				document.EfficientDocument efficientDoc = new document.EfficientDocument(chars);
				Double efficientDocScore = efficientDoc.getFleschScore();
			}
			endTime=System.nanoTime();
			efficientDocTime = (endTime - startTime)/100000000.0;
			
			//Print the results
			System.out.println(numToCheck + "\t\t" + basicDocTime + "\t" + efficientDocTime); 
			 
		}
	
	}
	
	/** Get a specified number of characters from a text file
	 * 
	 * @param filename The file to read from
	 * @param numChars The number of characters to read
	 * @return The text string from the file with the appropriate number of characters
	 */
	public static String getStringFromFile(String filename, int numChars) {
		
		StringBuffer s = new StringBuffer();
		try {
			FileInputStream inputFile= new FileInputStream(filename);
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			BufferedReader bis = new BufferedReader(inputStream);
			int val;
			int count = 0;
			while ((val = bis.read()) != -1 && count < numChars) {
				s.append((char)val);
				count++;
			}
			if (count < numChars) {
				System.out.println("Warning: End of file reached at " + count + " characters.");
			}
			bis.close();
		}
		catch(Exception e)
		{
		  System.out.println(e);
		  System.exit(0);
		}
		
		
		return s.toString();
	}
	
}
