import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",11000));
        while(true){
            Socket accept = serverSocket.accept();
            ObjectInputStream input = new ObjectInputStream(accept.getInputStream());
            Request req = (Request) input.readObject();
            Object result = null;
            if(req.getOp().equals("op2")){
                System.out.println("ENCAMINHADO OPERAÇÃO 2 PARA NODE 3 ENCAMINHAR");
                Socket node3 = new Socket("localhost", 12000);
                result = encaminhar(node3, req);
            }else if(req.getOp().equals("op1")){
                System.out.println("ENCAMINHADO OPERAÇÃO 1 PARA NODE 4 RESOLVER");
                Socket node4 = new Socket("localhost", 13000);
                result = encaminhar(node4, req);
            }

            ObjectOutputStream out = new ObjectOutputStream(accept.getOutputStream());
            out.writeObject(result);
            accept.close();
        }

        //serverSocket.close();
    }

    private static Object encaminhar(Socket socket, Request request) throws IOException, ClassNotFoundException {
        Object result;
        ObjectOutputStream outToNode3 = new ObjectOutputStream(socket.getOutputStream());
        outToNode3.writeObject(request);
        ObjectInputStream inNode3 = new ObjectInputStream(socket.getInputStream());
        result = inNode3.readObject();
        socket.close();
        return result;
    }
}
