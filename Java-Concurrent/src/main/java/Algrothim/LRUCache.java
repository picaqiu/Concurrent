package Algrothim;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private Map<K, Node> map;
    private DoubleList cache;
    private int capacity;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }

    private void addRecently(K key, V value) {
        Node node = new Node(key, value);
        cache.addLast(node);
        map.put(key, node);
    }

    //往LRU中插入数据
    public void put(K key, V value) {
        //key是否存在
        if (map.containsKey(key)) {
            Node node = map.get(key);
            //先删除，再插入到尾部
            cache.remove(node);
            addRecently(key, value);
            return;
        }
        if (cache.size() == capacity) {
            removeLeastRecently();
        }
        addRecently(key, value);
    }

    //删除
    public void delete(K key) {
        Node x = map.get(key);
        cache.remove(x);
        map.remove(key);
    }

    //使某key为最近使用
    public void makeRecently(K key) {
        //取出node，先从链表中删除，随后插入到尾节点
        Node x = map.get(key);
        delete(key);
        cache.addLast(x);
    }

    public void removeLeastRecently() {
        Node x = cache.removeFirst();
        map.remove(x.key);
    }
}
