package Algrothim;

import java.util.Arrays;

//大根堆
public class Heap {
    int[] data;
    int size;

    public Heap(int size) {
        this.size = 0;
        this.data = new int[size];
    }

    public boolean isFull() {
        return size == data.length;
    }

    public Integer pop() {
        if(size == 0){
            return null;
        }
        int ans = data[0];
        swap(data, 0, --size);
        heapify(data, 0);
        return ans;
    }

    public void push(int element) {
        if (isFull()) {
            throw new RuntimeException("the heap is full");
        }
        data[size] = element;
        buildHeap(data);
        size++;
    }

    /**
     * 找到根节点在堆中的位置
     *
     * @param nums
     * @param rootIndex
     */
    public void heapify(int[] nums, int rootIndex) {
        int left = 2 * rootIndex + 1;
        int right = left + 1;
        //最大值标记为根节点
        int largest = rootIndex;
        //比根节点大，则最大位置为左子节点位置
        if (left < size && nums[left] > nums[largest]) {
            largest = left;
        }
        //比根节点大，则最大位置为右子节点位置
        if (right < size && nums[right] > nums[largest]) {
            largest = right;
        }
        //最大节点不是根节点，交换根节点与最大节点的位置，递归，直到找到原根节点值的位置
        if (largest != rootIndex) {
            swap(nums, largest, rootIndex);
            heapify(nums, largest);
        }
    }


    /**
     * 自底向上构建，自顶向下比较时，上层比较时会缺少与叶子节点的比较，造成根节点不一定是最大的节点
     *
     * @param nums
     */
    public void buildHeap(int[] nums) {
        int length = size;
        for (int i = length / 2; i >= 0; i--) {
            heapify(nums, i);
        }
    }


    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 8, 5, 2, 4};
//        int[] nums2 = new int[]{5,8};
        System.out.println(Arrays.toString(nums));
        Heap heap = new Heap(nums.length);
        heap.buildHeap(nums);
        // buildMaxHeap(nums, nums.length);
        System.out.println(Arrays.toString(nums));

    }

}
