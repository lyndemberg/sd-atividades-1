
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class App {

    final static Logger LOG = Logger.getLogger(App.class.getName());
    static String opAvailable = "op1";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost",10000));
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
            response.setResult(resolverOperacao1(request.getNumero1(), request.getNumero2()));
            response.setSolved(true);
        }else{
            LOG.info("Não é possível resolver, encaminhando para node3");
            Socket socketToNode3 = new Socket("localhost",12000);

            ObjectOutputStream out = new ObjectOutputStream(socketToNode3.getOutputStream());
            out.writeObject(request);

            LOG.info("Recebendo resultado do node3");
            ObjectInputStream objectInputStreamNode3 = new ObjectInputStream(socketToNode3.getInputStream());

            response = (Response) objectInputStreamNode3.readObject();

            socketToNode3.close();
        }

        LOG.info("Encaminhando resultado");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream());
        objectOutputStream.writeObject(response);
        accept.close();
    }

    private static int resolverOperacao1(int x, int y){
        return  2 * y * x;
    }

}
