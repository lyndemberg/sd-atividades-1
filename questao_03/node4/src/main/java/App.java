import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",13000));
        while(true){
            Socket accept = serverSocket.accept();
            ObjectInputStream input = new ObjectInputStream(accept.getInputStream());
            Request req = (Request) input.readObject();
            Integer result = resolverRequest(req);
            ObjectOutputStream out = new ObjectOutputStream(accept.getOutputStream());
            out.writeObject(result);
            accept.close();
        }

        //serverSocket.close();

    }

    private static Integer resolverRequest(Request request){
        if(request.getOp().equals("op1"))
            return request.getNumero1() + request.getNumero2();
        else if(request.getOp().equals("op2"))
            return request.getNumero1() - request.getNumero2();
        else
            return 0;
    }
}
