import com.sun.javafx.binding.Logging;
import org.omg.CORBA.Object;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class App {
    static String opAvailable = "op2";
    final static Logger LOG = Logger.getLogger(App.class.getName());
    static SocketAddress node1 = new InetSocketAddress("localhost", 10000);
    static SocketAddress node2 = new InetSocketAddress("localhost", 11000);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",12000));
        while(true){
            Socket accept = server.accept();
            tratarRequisicao(accept);
        }
//        server.close();
    }

    private static void tratarRequisicao(Socket accept) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
        LOG.info("Recebeu nova requisição");
        Request request = (Request) objectInputStream.readObject();
        LOG.info(request.toString());

        Response response = new Response();
        if(request.getOp().equals(opAvailable)){
            LOG.info("Resolvendo operação");
            response.setResult(resolverOperacao2(request.getNumero1(), request.getNumero2()));
            response.setSolved(true);
        }else{
            LOG.info("Encaminhando a operação para NODE 1 ou 2");
            SocketAddress nodeForOperation1 = getNodeForOperation1();
            Socket socket = new Socket();
            socket.connect(nodeForOperation1);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(request);

            LOG.info("Recebendo resultado encaminhado");
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            response = (Response) in.readObject();
            socket.close();
        }

        LOG.info("Encaminhando resultado");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
        objectOutputStream.writeObject(response);
        accept.close();
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
