import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String opAvailable = "op1";

        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",11000));
        while(true){
            Socket accept = server.accept();
            InputStream inputStream = accept.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request request = (Request) objectInputStream.readObject();
            Object result = null;
            if(request.getOp().equals("op1")){
                Integer i = resolverOperacao1(request.getNumero1(), request.getNumero2());
                result = i;
            }else{
                Socket socketToNode3 = new Socket();
                socketToNode3.connect(new InetSocketAddress("localhost",12000));
                OutputStream outputStream = socketToNode3.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(request);

                InputStream inputStreamNode3 = socketToNode3.getInputStream();
                ObjectInputStream objectInputStreamNode3 = new ObjectInputStream(inputStreamNode3);
                result = objectInputStream.readObject();

                socketToNode3.close();
            }

            OutputStream outputStream = accept.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(result);

            accept.close();
        }
//        server.close();
    }

    private static int resolverOperacao1(int x, int y){
        return  2 * y * x;
    }

}
