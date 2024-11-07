// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            
            // Read input for encoding tree
            int n = scanner.nextInt();
            String letters = scanner.next();
            int[] probabilities = new int[n];
            for (int i = 0; i < n; i++) {
                probabilities[i] = scanner.nextInt();
            }
            String bitstream = scanner.next();
            
            // Create and use encoding tree
            EncodingTree tree = new EncodingTree(letters, probabilities);
            String decoded = tree.decode(bitstream);
            System.out.println(decoded);
            
            // Test Rolling Hash
            String s = "abbaba";
            int longestRepeating = RollingHash.findLongestRepeatingSubstring(s);
            System.out.println("Longest repeating substring length: " + longestRepeating);
            
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}