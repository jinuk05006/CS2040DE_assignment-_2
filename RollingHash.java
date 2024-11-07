// RollingHash.java
import java.util.Set;
import java.util.HashSet;

public class RollingHash {
    // Using large prime numbers to minimize collisions
    private static final long PRIME = 31;
    private static final long MOD = 1000000007;
    private static final long SECONDARY_MOD = 1000000009; // Secondary hash for double hashing
    
    /**
     * Find the length of the longest repeating substring
     * Time Complexity: O(n log n) where n is the length of the string
     * Space Complexity: O(n)
     */
    public static int findLongestRepeatingSubstring(String s) {
        int n = s.length();
        int left = 1;
        int right = n - 1;
        int result = 0;
        
        // Binary search on the length of the substring
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (hasRepeatingSubstring(s, mid)) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Check if there exists a repeating substring of given length
     * Using double hashing to reduce collisions
     * Time Complexity: O(n) where n is the length of the string
     */
    private static boolean hasRepeatingSubstring(String s, int length) {
        Set<Long> seen = new HashSet<>();
        long hash1 = 0;
        long hash2 = 0;
        long power1 = 1;
        long power2 = 1;
        
        // Calculate initial hash and power for both hash functions
        for (int i = 0; i < length; i++) {
            hash1 = (hash1 * PRIME + s.charAt(i)) % MOD;
            hash2 = (hash2 * PRIME + s.charAt(i)) % SECONDARY_MOD;
            if (i < length - 1) {
                power1 = (power1 * PRIME) % MOD;
                power2 = (power2 * PRIME) % SECONDARY_MOD;
            }
        }
        
        // Combine both hashes to reduce collisions
        long combinedHash = (hash1 << 32) | hash2;
        seen.add(combinedHash);
        
        // Use rolling hash to check all substrings of given length
        for (int i = length; i < s.length(); i++) {
            // Update first hash
            hash1 = (hash1 - (s.charAt(i - length) * power1 % MOD) + MOD) % MOD;
            hash1 = (hash1 * PRIME + s.charAt(i)) % MOD;
            
            // Update second hash
            hash2 = (hash2 - (s.charAt(i - length) * power2 % SECONDARY_MOD) + SECONDARY_MOD) % SECONDARY_MOD;
            hash2 = (hash2 * PRIME + s.charAt(i)) % SECONDARY_MOD;
            
            combinedHash = (hash1 << 32) | hash2;
            
            // Double check actual substrings only when hash collision occurs
            if (seen.contains(combinedHash)) {
                String current = s.substring(i - length + 1, i + 1);
                for (int j = 0; j < i - length + 1; j++) {
                    if (s.substring(j, j + length).equals(current)) {
                        return true;
                    }
                }
            }
            seen.add(combinedHash);
        }
        
        return false;
    }
}