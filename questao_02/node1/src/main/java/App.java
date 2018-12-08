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
        server.bind(new InetSocketAddress("localhost",10000));
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
            }else{
                LOG.info("Não é possível resolver, encaminhando para node3");
                Socket socketToNode3 = new Socket();
                socketToNode3.connect(new InetSocketAddress("localhost",12000));
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketToNode3.getOutputStream());
                objectOutputStream.writeObject(request);

                LOG.info("Recebendo resultado do node3");
                InputStream inputStreamNode3 = socketToNode3.getInputStream();
                ObjectInputStream objectInputStreamNode3 = new ObjectInputStream(inputStreamNode3);
                result = (Integer) objectInputStream.readObject();
                socketToNode3.close();
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
