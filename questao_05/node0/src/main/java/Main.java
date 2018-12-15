import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Node 0 iniciou");
        // SENDING OPERATION SUM
        Socket socket = new Socket("localhost",10000);
        Request request = new Request("sum",100,20);
        System.out.println("Node 0 envia "+ request + " para Node1");
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(request);
        socket.close();

        // SENDING OPERATION DIFF
        Socket socket2 = new Socket("localhost",11000);
        Request request2 = new Request("diff",100,20);
        System.out.println("Node 0 envia "+ request2 + " para Node2");
        ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
        out2.writeObject(request2);
        socket2.close();

    }
}
