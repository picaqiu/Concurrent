public class WhiteCat extends Cat {
    private String name;

    public WhiteCat(String name) {
        super(name);
        this.name = name;
    }

    public static void main(String[] args) {
        Cat cat = new WhiteCat("dabai");
        cat.speak();
    }

    public void speak() {
        System.out.println("Miu miu miu" + "and my color is white " + name);
    }
}
