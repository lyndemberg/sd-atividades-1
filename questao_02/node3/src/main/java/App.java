import com.sun.istack.internal.logging.Logger;
import org.omg.CORBA.Object;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class App {
    final static Logger LOG = Logger.getLogger(App.class);
    static String opAvailable = "op2";
    static SocketAddress node1 = new InetSocketAddress("localhost", 10000);
    static SocketAddress node2 = new InetSocketAddress("localhost", 11000);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",12000));
        while(true){
            Socket accept = server.accept();
            LOG.info("Recebeu nova requisição");
            ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
            Request request = (Request) objectInputStream.readObject();
            LOG.info(request.toString());

            Integer result = null;
            if(request.getOp().equals(opAvailable)){
                LOG.info("Resolvendo operação");
                Integer i = resolverOperacao2(request.getNumero1(), request.getNumero2());
                result = i;
            }else{
                LOG.info("Encaminhando a operação para NODE 1 ou 2");
                SocketAddress nodeForOperation1 = getNodeForOperation1();
                Socket socket = new Socket();
                socket.connect(nodeForOperation1);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(request);

                LOG.info("Recebendo resultado encaminhado");
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                result = (Integer) in.readObject();
                socket.close();
            }

            LOG.info("Encaminhando resultado");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
            objectOutputStream.writeObject(result);

            accept.close();
        }
//        server.close();
    }

    private static int resolverOperacao2(int x, int y){
        return  2 * x / y;
    }

    private static SocketAddress getNodeForOperation1(){
        Random random = new Random();
        int i = random.nextInt(3);
        if(i==1)
            return node1;
        else
            return node2;
    }

}
