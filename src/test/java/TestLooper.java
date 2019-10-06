import util.Looper;

/**
 * @author Yangzhe Xie
 * @date 6/10/19
 */
public class TestLooper {
    public static void main(String[] args) {
        Looper looper = new Looper();
        looper.post(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello Again");
        });
        System.out.println("Hello");
    }
}
