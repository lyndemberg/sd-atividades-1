import javax.management.monitor.Monitor;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws IOException {
        Socket node1 = new Socket("localhost", 10444);
        CheckService checkService = new CheckService(node1, "./tmp");
        checkService.start();
    }
}
