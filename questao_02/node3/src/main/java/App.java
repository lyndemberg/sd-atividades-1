import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class App {

    static SocketAddress node1 = new InetSocketAddress("localhost", 10000);
    static SocketAddress node2 = new InetSocketAddress("localhost", 11000);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String opAvailable = "op2";
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",12000));

        while(true){
            Socket accept = server.accept();
            InputStream inputStream = accept.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request request = (Request) objectInputStream.readObject();
            Object result = null;
            if(request.getOp().equals(opAvailable)){
                Integer i = resolverOperacao2(request.getNumero1(), request.getNumero2());
                result = i;
            }else{
                //ENCAMINHA A OPERAÇÃO 1 PARA O NODE 1 OU 2
            }

            OutputStream outputStream = accept.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(result);

            accept.close();
        }
//        server.close();
    }

    private static int resolverOperacao2(int x, int y){
        return  2 * x / y;
    }

}
