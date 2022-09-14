public class Cat extends Animal {
    private String name;

    public Cat(String name) {
        super(name);
        this.name = name;
    }

    public void speak() {
        System.out.println("Miu miu miu " + name);
    }
}
