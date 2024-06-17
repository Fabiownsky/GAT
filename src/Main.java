import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Hello, World!");
            System.out.println("Press Enter to exit...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
