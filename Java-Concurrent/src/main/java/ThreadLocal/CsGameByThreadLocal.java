package ThreadLocal;

public class CsGameByThreadLocal {
    private static final Integer BULLET_NUMBER = 1500;
    private static final ThreadLocal<Integer> NUMBER = ThreadLocal.withInitial(() -> BULLET_NUMBER);

    public static void main(String[] args) {

    }
}
