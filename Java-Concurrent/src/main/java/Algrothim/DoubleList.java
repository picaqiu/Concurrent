package Algrothim;

public class DoubleList {
    private Node head, tail;
    private int size;

    public DoubleList() {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.pre = head;
        size = 0;
    }

    public void addLast(Node node) {
        node.pre = tail.pre;
        node.next = tail;
        tail.pre.next = node;
        tail.pre = node;
        size++;
    }

    public void remove(Node x) {
        x.pre.next = x.next;
        x.next.pre = x.pre;
        x.next = null;
        x.pre = null;
        size--;
    }

    public Node removeFirst() {
        if (head.next == tail) {
            return null;
        }
        Node node = head.next;
        head.next = node.next;
        node.next.pre = head;
        node.pre = null;
        node.next = null;
        size--;
        return node;
    }

    public int size() {
        return size;
    }

}
