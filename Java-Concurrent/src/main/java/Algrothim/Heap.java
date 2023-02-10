package Algrothim;

//大根堆
public class Heap {
    int[] data;
    int size;

    public Heap(int size) {
        this.size = size;
        this.data = new int[size];
    }

    public void heapfy(int[] nums, int rootIndex) {
        int left = 2 * rootIndex + 1;
        int right = left + 1;
        int largest = rootIndex;
        if (left < size && nums[left] > nums[rootIndex]) {
            largest = left;
        }
        if (right < size && nums[right] > nums[largest]) {
            largest = right;
        }
        if (largest != rootIndex) {
            swap(nums, largest, rootIndex);
            heapfy(nums, largest);
        }
    }

    

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
