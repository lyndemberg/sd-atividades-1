import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",13440));

        while(true){
            Socket accept = serverSocket.accept();


            accept.close();
        }


        //serverSocket.close();
    }
}
