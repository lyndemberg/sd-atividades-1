import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String opAvailable = "op1";

        Map<String,String> fowardsOperations = new HashMap<String, String>();

        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",10000));
        while(true){
            Socket accept = server.accept();

            InputStream inputStream = accept.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Request request = (Request) objectInputStream.readObject();
            if(request.getOp().equals("op1")){
                Integer i = resolverOperacao1(request.getNumero1(), request.getNumero2());
                OutputStream outputStream = accept.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(i);
            }else{

            }

            accept.close();
        }
//        server.close();
    }

    private static int resolverOperacao1(int x, int y){
        return  2 * y * x;
    }

    private static getNodeForOperation(){

    }
}
