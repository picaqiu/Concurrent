package Algrothim;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MaxHeap {
    private int[] heap;
    private int size;
    private final int limit;

    public MaxHeap(int limit) {
        this.heap = new int[limit];
        this.size = 0;
        this.limit = limit;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == limit;
    }

    public void push(int element) {
        if (isFull()) {
            throw new RuntimeException("the heap is full");
        }
        heap[size] = element;
        heapInsert(heap, size++);
    }

    public int pop() {
        int ans = heap[0];
        swap(heap, 0, --size);
        heapify(heap, 0, size);
        return ans;
    }

    private void heapify(int[] heap, int rootIndex, int size) {
        int leftChild = rootIndex * 2 + 1;
        while (leftChild < size) {
            int largerChild = leftChild + 1 < size && heap[leftChild + 1] > heap[leftChild] ? leftChild + 1 : leftChild;
            if (heap[rootIndex] < heap[largerChild]) {
                swap(heap, rootIndex, largerChild);
                rootIndex = largerChild;
                leftChild = 2 * rootIndex + 1;
            }else{
                break;
            }
        }
    }

    //新进来的数停留在index位置上，需要往上移动
    private void heapInsert(int[] heap, int index) {
        while (heap[index] > heap[(index - 1) / 2]) {
            swap(heap, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void swap(int[] heap, int index1, int index2) {
        int temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        System.out.println(queue.peek());
        List<Integer> data = new ArrayList<>();
        data.toArray();

    }
}
