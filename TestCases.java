// TestCases.java
public class TestCases {
    private static void assertEqual(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
        System.out.println("PASS: " + message);
    }

    public static void testCustomHeap() {
        System.out.println("\nTesting Custom Heap...");
        CustomHeap heap = new CustomHeap();
        
        // Test insertion and extraction
        heap.insert(new CustomHeap.Node('A', 39));
        heap.insert(new CustomHeap.Node('B', 21));
        heap.insert(new CustomHeap.Node('C', 19));
        
        CustomHeap.Node[] smallest = heap.extractTwoSmallest();
        assertEqual('C', smallest[0].letter, "First smallest should be C");
        assertEqual('B', smallest[1].letter, "Second smallest should be B");
        
        // Test equal probabilities
        heap = new CustomHeap();
        heap.insert(new CustomHeap.Node('A', 20));
        heap.insert(new CustomHeap.Node('B', 20));
        
        smallest = heap.extractTwoSmallest();
        assertEqual('A', smallest[0].letter, "With equal probabilities, A should come first (lexicographical order)");
        assertEqual('B', smallest[1].letter, "With equal probabilities, B should come second");
    }
    
    public static void testEncodingTree() {
        System.out.println("\nTesting Encoding Tree...");
        // Test case from assignment
        String letters = "ABCDE";
        int[] probabilities = {39, 21, 19, 12, 9};
        String bitstream = "101100000111";
        
        EncodingTree tree = new EncodingTree(letters, probabilities);
        String result = tree.decode(bitstream);
        assertEqual("DECBA", result, "Sample test case 1");
        
        // Additional test case
        letters = "ABCDE";
        int[] probabilities2 = {20, 20, 30, 10, 20};
        bitstream = "11101100010";
        
        tree = new EncodingTree(letters, probabilities2);
        result = tree.decode(bitstream);
        assertEqual("AECBC", result, "Sample test case 2");
    }
    
    public static void testRollingHash() {
        System.out.println("\nTesting Rolling Hash...");
        // Test case 1: No repeating substring
        assertEqual(0, RollingHash.findLongestRepeatingSubstring("abcd"), 
                   "String with no repeating substring");
        
        // Test case 2: Multiple repeating substrings
        assertEqual(2, RollingHash.findLongestRepeatingSubstring("abbaba"), 
                   "String with multiple repeating substrings");
        
        // Test case 3: Longer repeating substring
        assertEqual(3, RollingHash.findLongestRepeatingSubstring("aabcaabdaab"), 
                   "String with longer repeating substring");
        
        // Test case 4: Edge cases
        assertEqual(0, RollingHash.findLongestRepeatingSubstring("a"), 
                   "Single character string");
        assertEqual(1, RollingHash.findLongestRepeatingSubstring("aa"), 
                   "Two identical characters");
    }
    
    public static void main(String[] args) {
        try {
            System.out.println("Starting tests...");
            
            testCustomHeap();
            testEncodingTree();
            testRollingHash();
            
            System.out.println("\nAll tests passed successfully!");
            
        } catch (AssertionError e) {
            System.err.println("\nTest failed: " + e.getMessage());
            System.exit(1);
        }
    }
}