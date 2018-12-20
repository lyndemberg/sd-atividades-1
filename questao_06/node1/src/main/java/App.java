import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",10444));

        while(true){
            Socket accept = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(accept.getInputStream());
            Event event = (Event) in.readObject();
            System.out.println(event.toString());
            accept.close();
        }


        //serverSocket.close();
    }
}
