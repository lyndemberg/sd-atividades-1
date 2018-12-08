import javax.annotation.PostConstruct;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {

    private static List<Node> nodes =  new ArrayList<>();

    @PostConstruct
    public void initialize(){
        nodes.add(new Node(new InetSocketAddress("localhost",10000),"node1",""));
        nodes.add(new Node(new InetSocketAddress("localhost",11000),"node2","node1"));
        nodes.add(new Node(new InetSocketAddress("localhost",12000),"node3",""));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Request request = new Request("op1", 2, 3);
        Socket socket = new Socket();
        Node nodeRandomForRequest = getNodeRandomForRequest(nodes);
        boolean available = isAvailable(nodeRandomForRequest);

        if(available){
            socket.connect(nodeRandomForRequest.getAddress());
        }else{
            try {
                Node nodeNonReplica = getNodeNonReplica(nodeRandomForRequest.getNode());
                socket.connect(nodeNonReplica.getAddress());
            } catch (NonReplicaAvailableException e) {
                System.out.println(e.getMessage());
            }
        }

        //ENVIANDO OPERAÇÃO
        OutputStream outToServer = socket.getOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(outToServer);
        objectOut.writeObject(request);

        //RECEBENDO RESULTADO
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Integer result = (Integer) objectInputStream.readObject();


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
