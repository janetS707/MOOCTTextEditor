package spelling;

import java.util.List;

public class NearbyWordsGraderTwo {
    public static void main(String args[]) {
              try {
        	System.out.println("grader_output/module5.part2.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            Dictionary d = new DictionaryHashSet();
            DictionaryLoader.loadDictionary(d, "test_cases/dict2.txt");
            NearbyWords nw = new NearbyWords(d);

            System.out.println("** Test 1: 2 suggestions... ");
            List<String> d1 = nw.suggestions("dag", 4);
            System.out.println( "" + d1.size() + " suggestions returned.");
            System.out.println(d1.toString());
            System.out.println( "\n");
            
            System.out.println( "** Test 2: Checking suggestion correctness... ");
            System.out.println( "Suggestions: ");
            for (String i : d1) {
            	System.out.println( i + ", ");
            }

            System.out.println( "\n** Test 3: 3 suggestions... ");
            d1 = nw.suggestions("fare", 3);
            System.out.println( "" + d1.size() + " suggestions returned.\n");

            System.out.println( "** Test 4: Checking suggestion correctness... ");
            System.out.println( "Suggestions: ");
            for (String i : d1) {
                System.out.println( i + ", ");
            }
            System.out.println( "\n");
            
        } catch (Exception e) {
        	System.out.println("Runtime error: " + e);
            return;
        }

        System.out.println( "Tests complete. Make sure everything looks right.");
        
    }
}
