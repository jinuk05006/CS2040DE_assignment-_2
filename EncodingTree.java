// EncodingTree.java
import java.util.Map;
import java.util.HashMap;

public class EncodingTree {
    private CustomHeap.Node root;
    private Map<Character, String> encodingMap;
    
    /**
     * Construct the encoding tree from letters and probabilities
     * Time Complexity: O(n log n) where n is the number of letters
     */
    public EncodingTree(String letters, int[] probabilities) {
        CustomHeap heap = new CustomHeap();
        encodingMap = new HashMap<>();
        
        // Create leaf nodes for each letter
        for (int i = 0; i < letters.length(); i++) {
            heap.insert(new CustomHeap.Node(letters.charAt(i), probabilities[i]));
        }
        
        // Build the tree by combining nodes
        while (heap.size() > 1) {
            CustomHeap.Node[] smallest = heap.extractTwoSmallest();
            if (smallest.length < 2) break;  // Safety check
            
            CustomHeap.Node left = smallest[0];
            CustomHeap.Node right = smallest[1];
            
            // Ensure left child has smaller probability than right child
            if (left.probability > right.probability) {
                CustomHeap.Node temp = left;
                left = right;
                right = temp;
            }
            
            CustomHeap.Node combined = new CustomHeap.Node(
                left.probability + right.probability,
                left,
                right
            );
            heap.insert(combined);
        }
        
        CustomHeap.Node[] finalNode = heap.extractTwoSmallest();
        if (finalNode.length > 0) {
            root = finalNode[0];
            buildEncodingMap(root, "");
        }
    }
    
    /**
     * Build the encoding map by traversing the tree
     * Time Complexity: O(n) where n is the number of nodes
     */
    private void buildEncodingMap(CustomHeap.Node node, String code) {
        if (node == null) return;
        
        if (!node.isInternal) {
            encodingMap.put(node.letter, code);
            return;
        }
        
        buildEncodingMap(node.left, code + "0");
        buildEncodingMap(node.right, code + "1");
    }
    
    /**
     * Decode a bitstream using the encoding tree
     * Time Complexity: O(m) where m is the length of the bitstream
     */
    public String decode(String bitstream) {
        if (root == null) return "";
        
        StringBuilder result = new StringBuilder();
        CustomHeap.Node current = root;
        
        for (char bit : bitstream.toCharArray()) {
            if (bit == '0') {
                current = current.left;
            } else {
                current = current.right;
            }
            
            if (!current.isInternal) {
                result.append(current.letter);
                current = root;
            }
        }
        
        return result.toString();
    }
}