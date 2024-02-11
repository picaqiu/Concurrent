package Algrothim.array;

import java.util.Arrays;

public class ArrayList<E> {

    private Object[] data;

    private int size;

    public ArrayList() {
    }

    public ArrayList(int length) {
        data = new Object[length];
    }

    public void add(E num) {
        ensureCapacity(size);
        data[size++] = num;

    }

    public E get(int index) {
        if (index > size - 1 || index < 0) {
            throw new IllegalArgumentException("index out range of the array");
        }
        return (E) data[index];
    }

    public void remove(int index) {
        if (index > size - 1) {
            throw new IllegalArgumentException("index out range of the array");
        }
        int movedLen = size - index - 1;
        if (movedLen > 0) {
            System.arraycopy(data, index + 1, data, index, movedLen);
        }
        data[size--] = null;
    }

    private void ensureCapacity(int size) {
        if (size > data.length) {
            grow(data.length);
        }
    }

    private void grow(int length) {
        int newLength = Integer.MAX_VALUE >> 1 > length ? Integer.MAX_VALUE : length << 1;
        data = Arrays.copyOf(data, newLength);
    }

}
