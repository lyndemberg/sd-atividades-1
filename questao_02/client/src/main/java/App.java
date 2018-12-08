



import com.sun.istack.internal.logging.Logger;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {

    final static Logger LOG = Logger.getLogger(App.class);
    private static List<Node> nodes =  new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        nodes.add(new Node(new InetSocketAddress("localhost",10000),"node1",""));
        nodes.add(new Node(new InetSocketAddress("localhost",11000),"node2","node1"));
        nodes.add(new Node(new InetSocketAddress("localhost",12000),"node3",""));

        Request request = new Request("op2", 2, 3);
        boolean deveEnviar = false;
        Node nodeRandomForRequest = getNodeRandomForRequest(nodes);
        LOG.info("Nó aleatório para a requisição-->" + nodeRandomForRequest);
        boolean available = isAvailable(nodeRandomForRequest);

        Socket socket = new Socket();
        if(available){
            LOG.info("Nó está disponível!");
            socket.connect(nodeRandomForRequest.getAddress());
            deveEnviar = true;
        }else{
            LOG.severe("Nó não está disponível!");
            try {
                LOG.info("Buscando nó não réplica......");
                Node nodeNonReplica = getNodeNonReplica(nodeRandomForRequest.getNode());
                socket.connect(nodeNonReplica.getAddress());
                deveEnviar = true;
            } catch (NonReplicaAvailableException e) {
                LOG.severe("Não há nenhum nó não réplica");
            }
        }

        if(deveEnviar){
            LOG.info("Enviando requisição: " + request);
            ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
            objectOut.writeObject(request);

            LOG.info("Recebendo valor resultado...");
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Integer result = (Integer) objectInputStream.readObject();
            System.out.println("O resultado é: " + result);
        }

        socket.close();
    }

    private static boolean isAvailable(Node node){
        boolean isAvailable = false;
        Socket socket = new Socket();
        int timeout = 2000;
        try{
            socket.connect(node.getAddress(), timeout);
            socket.close();
            isAvailable = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isAvailable;
    }

    private static Node getNodeNonReplica(String node) throws NonReplicaAvailableException {
        List<Node> listNonReplica = new ArrayList<>();
        for(Node n : nodes){
            if(!n.getNode().equals(node) && !n.getReplicaOf().equals(node))
                listNonReplica.add(n);
        }

        if(listNonReplica.isEmpty())
            throw new NonReplicaAvailableException("Nenhum nó não-réplica disponível");

        return getNodeRandomForRequest(listNonReplica);
    }

    private static Node getNodeRandomForRequest(List<Node> nodesList){
        Random random = new Random();
        Node node = nodesList.get(random.nextInt(nodesList.size()));
        return node;
    }

}
