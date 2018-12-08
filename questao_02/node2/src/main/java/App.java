import com.sun.istack.internal.logging.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class App {

    final static Logger LOG = Logger.getLogger(App.class);
    static String opAvailable = "op1";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",11000));
        while(true){
            Socket accept = server.accept();
            LOG.info("Recebeu nova requisição");
            ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
            Request request = (Request) objectInputStream.readObject();
            LOG.info(request.toString());

            Integer result = null;
            if(request.getOp().equals(opAvailable)){
                LOG.info("Resolvendo operação");
                Integer i = resolverOperacao1(request.getNumero1(), request.getNumero2());
                result = i;
            }

            LOG.info("Encaminhando resultado");
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
