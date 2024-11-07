# CS2040DE Assignment 2 - Heap and Hash

## Code Repository
All Java implementations can be found in this GitHub repository:
[https://github.com/yourusername/CS2040DE_Assignment2](https://github.com/yourusername/CS2040DE_Assignment2)

## Part 2.a Custom Heap

### Task 1: Custom Heap Structure

#### Implementation Overview
The custom heap is implemented with these special properties:
1. Root contains the smallest element (min-heap property)
2. Left child must always be smaller than right child
3. Priority rules for nodes with equal probabilities:
   - Letter nodes have priority over internal nodes
   - Between letter nodes: Use lexicographical order
   - Between internal nodes: Follow FIFO principle

```java
// Key class structure (see full implementation in GitHub repository)
public class CustomHeap {
    private List<Node> heap;
    
    static class Node implements Comparable<Node> {
        char letter;
        int probability;
        boolean isInternal;
        Node left, right;
        long creationTime;
    }
}
```

#### Analysis
- Time Complexity:
  * Insertion: O(log n)
  * Extract minimum: O(log n)
  * Extract two smallest: O(log n)
- Space Complexity: O(n)

#### Design Choices
1. Used ArrayList for dynamic heap storage
2. Implemented Comparable interface for flexible node comparison
3. Added timestamp for FIFO ordering of internal nodes
4. Maintained encapsulation through private helper methods

### Task 2: Encoding Tree Construction and Decoding

#### Binary Tree Construction
The encoding-decoding tree is built using these rules:
1. Two types of nodes:
   - Letter Nodes: Store actual letters and probabilities
   - Internal Nodes: Combined probability of children
2. Construction process:
   - Start with leaf nodes for each letter
   - Repeatedly combine two smallest probabilities
   - Ensure left child probability < right child probability

#### Node Selection Implementation
```java
// Priority handling in Node's compareTo method
public int compareTo(Node other) {
    if (this.probability != other.probability) {
        return Integer.compare(this.probability, other.probability);
    }
    if (this.isInternal != other.isInternal) {
        return this.isInternal ? 1 : -1;
    }
    if (!this.isInternal) {
        return Character.compare(this.letter, other.letter);
    }
    return Long.compare(this.creationTime, other.creationTime);
}
```

#### Sample Test Cases

1. First Sample:
```
Input:
5
ABCDE
39 21 19 12 9
101100000111

Output: DECBA

Tree Structure:
[100]
/0 \1
[40] [60]
/0 \1 0/ \1
[19] [21] [21] [39]
(C) (B) [E+D] (A)
/0 \1
[9] [12]
(E) (D)
```

2. Second Sample:
```
Input:
5
ABCDE
20 20 30 10 20
11101100010

Output: AECBC

Tree Structure:
[100]
/0 \1
[40] [60]
/0 \1 0/ \1
[20] [20] [30] [30]
(B) (E) (C) [D+A]
/0 \1
[10] [20]
(D) (A)
```

## Part 2.b Rolling Hash

### Part 1: Sliding Window Technique

The implementation improves time complexity from O(n * L) to O(n) using rolling hash:

Original Approach (O(n * L)):
```java
// Calculate hash for each window independently
for (int i = 0; i < n - L + 1; i++) {
    long hash = 0;
    for (int j = 0; j < L; j++) {
        hash = (hash * PRIME + s.charAt(i + j)) % MOD;
    }
}
```

Optimized Approach (O(n)):
```java
// Rolling calculation
long hash = 0;
// Calculate initial window
for (int i = 0; i < L; i++) {
    hash = (hash * PRIME + s.charAt(i)) % MOD;
}
// Roll the window
for (int i = L; i < n; i++) {
    hash = ((hash - s.charAt(i-L) * power % MOD + MOD) % MOD * PRIME 
            + s.charAt(i)) % MOD;
}
```

### Part 2: Hash Collision Handling

#### Primary Method: Double Hashing
```java
private static final long PRIME = 31;
private static final long MOD = 1000000007;
private static final long SECONDARY_MOD = 1000000009;

// Combined hash calculation
long hash1 = calculateFirstHash();
long hash2 = calculateSecondHash();
long combinedHash = (hash1 << 32) | hash2;
```

#### Collision Resolution
1. Use two independent hash functions
2. Combine hashes to reduce collision probability
3. Verify actual string equality on collision
4. Analysis:
   - Collision probability: ~1/2^64
   - Minimal performance impact due to rare collisions
   - Guaranteed correctness through string comparison

### Part 3: Problem Solution

#### Implementation for Longest Repeating Substring
```java
public static int findLongestRepeatingSubstring(String s) {
    int n = s.length();
    // Binary search for length
    int left = 1, right = n - 1;
    int result = 0;
    
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
```

#### Test Cases
1. No repeating substring:
```
Input: "abcd"
Output: 0
Explanation: No substring appears more than once
```

2. Multiple repeating substrings:
```
Input: "abbaba"
Output: 2
Explanation: "ab" and "ba" each appear twice
```

3. Longer repeating substring:
```
Input: "aabcaabdaab"
Output: 3
Explanation: "aab" appears three times
```

## Performance Analysis

### Time Complexity
1. Custom Heap:
   - Build: O(n log n)
   - Extract operations: O(log n)

2. Encoding Tree:
   - Construction: O(n log n)
   - Decoding: O(m) where m is bitstream length

3. Rolling Hash:
   - Overall: O(n log n)
   - Each length check: O(n)
   - Binary search iterations: O(log n)

### Space Complexity
1. Custom Heap: O(n)
2. Encoding Tree: O(n)
3. Rolling Hash: O(n)

## Running the Code

1. Clone the repository:
```bash
git clone https://github.com/yourusername/CS2040DE_Assignment2.git
```

2. Compile:
```bash
javac *.java
```

3. Run main program:
```bash
java Main
```

4. Run tests:
```bash
java TestCases
```

Note: Replace `yourusername` in the GitHub URL with your actual GitHub username once you've created the repository.
