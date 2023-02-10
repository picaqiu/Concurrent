package Algrothim;

public class Trie {
    private Trie[] children;
    private boolean isEnd;

    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }

    public void insert(String word) {
        Trie root = this;
        int n = word.length();
        char[] ch = word.toCharArray();
        for (int i = 0; i < n; i++) {
            //计算index
            int index = ch[i] - 'a';
            if (root.children[index] == null) {
                root.children[index] = new Trie();
            }
            root = root.children[index];
        }
        root.isEnd = true;
    }

    public boolean search(String target){
        Trie root = this;
        char[] ch = target.toCharArray();
        for (int i=0;i<ch.length;i++){
            int index = ch[i] - 'a';
            if (root.children[index] == null){
                return  false;
            }
            root = root.children[index];
        }
        return  root.isEnd;
    }

    public static void main(String[] args) {
        Trie root = new Trie();
        root.insert("abc");
        root.insert("adc");
        root.insert("apl");
        root.insert("bbc");
        root.insert("dog");
        System.out.println(root.search("abc"));
        System.out.println(root.search("abd"));
        System.out.println(root.search("adc"));
        System.out.println(root.search("bbc"));
        System.out.println();
    }
}
