// CustomHeap.java
import java.util.*;

/**
 * Custom Heap implementation for Assignment 2
 * Properties:
 * 1. Root contains the smallest element (min-heap property)
 * 2. Left child must always be smaller than right child
 */
public class CustomHeap {
    private List<Node> heap;
    
    /**
     * Node class to store probability and letter information
     */
    static class Node implements Comparable<Node> {
        char letter;  // Letter for leaf nodes
        int probability;  // Probability value
        boolean isInternal;  // Flag to identify internal nodes
        Node left, right;  // Children nodes
        long creationTime;  // For FIFO ordering of internal nodes
        
        public Node(char letter, int probability) {
            this.letter = letter;
            this.probability = probability;
            this.isInternal = false;
            this.creationTime = System.nanoTime();
        }
        
        public Node(int probability, Node left, Node right) {
            this.probability = probability;
            this.isInternal = true;
            this.left = left;
            this.right = right;
            this.creationTime = System.nanoTime();
        }
        
        @Override
        public int compareTo(Node other) {
            if (this.probability != other.probability) {
                return Integer.compare(this.probability, other.probability);
            }
            // If probabilities are equal, prioritize letter nodes over internal nodes
            if (this.isInternal != other.isInternal) {
                return this.isInternal ? 1 : -1;
            }
            // For letter nodes with same probability, use lexicographical order
            if (!this.isInternal) {
                return Character.compare(this.letter, other.letter);
            }
            // For internal nodes with same probability, use FIFO
            return Long.compare(this.creationTime, other.creationTime);
        }
    }
    
    public CustomHeap() {
        this.heap = new ArrayList<>();
    }
    
    /**
     * Insert a new node into the heap while maintaining heap properties
     * Time Complexity: O(log n)
     */
    public void insert(Node node) {
        heap.add(node);
        heapifyUp(heap.size() - 1);
    }
    
    /**
     * Extract the two smallest values from the heap
     * Time Complexity: O(log n)
     */
    public Node[] extractTwoSmallest() {
        if (heap.size() < 2) {
            if (heap.size() == 1) {
                Node[] result = new Node[1];
                result[0] = extractMin();
                return result;
            }
            return new Node[0];
        }
        
        Node[] result = new Node[2];
        result[0] = extractMin();
        result[1] = extractMin();
        return result;
    }
    
    /**
     * Extract the minimum element from the heap
     * Time Complexity: O(log n)
     */
    private Node extractMin() {
        if (heap.isEmpty()) {
            return null;
        }
        
        Node min = heap.get(0);
        Node last = heap.remove(heap.size() - 1);
        
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        
        return min;
    }
    
    /**
     * Maintain heap property by moving a node up
     * Time Complexity: O(log n)
     */
    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIdx = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIdx)) < 0) {
                swap(index, parentIdx);
                index = parentIdx;
            } else {
                break;
            }
        }
    }
    
    /**
     * Maintain heap property by moving a node down
     * Time Complexity: O(log n)
     */
    private void heapifyDown(int index) {
        int size = heap.size();
        while (true) {
            int smallest = index;
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            
            if (leftChild < size && heap.get(leftChild).compareTo(heap.get(smallest)) < 0) {
                smallest = leftChild;
            }
            if (rightChild < size && heap.get(rightChild).compareTo(heap.get(smallest)) < 0) {
                smallest = rightChild;
            }
            
            if (smallest == index) {
                break;
            }
            
            swap(index, smallest);
            index = smallest;
        }
    }
    
    private void swap(int i, int j) {
        Node temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    public int size() {
        return heap.size();
    }
}