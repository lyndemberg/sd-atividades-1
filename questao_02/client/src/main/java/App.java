import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class App {

    final static Logger LOG = Logger.getLogger(App.class.getName());
    private static List<Node> nodes =  new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        nodes.add(new Node(new InetSocketAddress("localhost",10000),"node1",""));
        nodes.add(new Node(new InetSocketAddress("localhost",11000),"node2","node1"));
        nodes.add(new Node(new InetSocketAddress("localhost",12000),"node3",""));

        Request request = new Request("op1", 10, 3);
        Node nodeRandomForRequest = getNodeRandomForRequest(nodes);
        LOG.info("Nó aleatório para a requisição-->" + nodeRandomForRequest);

        Socket socket = new Socket();

        try{
            socket = new Socket(nodeRandomForRequest.getAddress().getHostName(), nodeRandomForRequest.getAddress().getPort());
        }catch (ConnectException ex){
            LOG.severe("Nó não está disponível!");
            LOG.info("Buscando nó não réplica......");
            try {
                Node nodeNonReplica = getNodeNonReplica(nodeRandomForRequest.getNode());
                socket = new Socket(nodeNonReplica.getAddress().getHostName(),nodeNonReplica.getAddress().getPort());
            } catch (NonReplicaAvailableException e) {
                LOG.severe("Não existe nenhum nó não-réplica");
            } catch (ConnectException ex2){
                LOG.severe("Nó não réplica não está disponível");
            }
        }

        LOG.info("Enviando requisição: " + request);
        ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectOut.writeObject(request);

        LOG.info("Recebendo resultado...");
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Response response = (Response) objectInputStream.readObject();

        System.out.println(response.toString());

        socket.close();
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
