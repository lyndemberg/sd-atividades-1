import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws IOException {

        User user = new User("João");
        long startTime = System.currentTimeMillis();
        for(int i=0; i<1000; i++){
            user.setCode(i);
            Socket socket = new Socket("localhost",11000);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(user);
            socket.close();
        }
        long endTime = System.currentTimeMillis();
        long seconds = (endTime - startTime) / 1000;
        System.out.println("Durou " + seconds + " segundos");


    }
}
