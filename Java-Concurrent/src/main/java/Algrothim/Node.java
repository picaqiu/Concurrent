package Algrothim;

public class Node<K, V> {
    public K key;
    public V value;
    Node pre, next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
