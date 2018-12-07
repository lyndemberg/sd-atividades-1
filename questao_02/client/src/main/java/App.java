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
        nodes.add(new Node(new InetSocketAddress("localhost",10000),"node1"));
        nodes.add(new Node(new InetSocketAddress("localhost",11000),"replica-node1"));
        nodes.add(new Node(new InetSocketAddress("localhost",12000),"node3"));
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Request request = new Request("op1", 2, 3);
        Socket socket = new Socket();
        Node nodeRandomForRequest = getNodeRandomForRequest();
        boolean available = isAvailable(nodeRandomForRequest);

        if(available){
            socket.connect(nodeRandomForRequest.getAddress());
        }else{
            try {
                Node nodeNonReplica = getNodeNonReplica(nodeRandomForRequest.getDescription());
                socket.connect(nodeNonReplica.getAddress());
            } catch (NonReplicaAvailableException e) {
                e.printStackTrace();
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

    private static Node getNodeNonReplica(String description) throws NonReplicaAvailableException {
        Node node = null;
        for(Node n : nodes){
            String[] split = n.getDescription().split("-");
            if(!split[0].equals("replica") && !split[1].equals(description)) {
                node = n;
            }
        }

        if(node == null)
            throw new NonReplicaAvailableException("Nenhum nó não-réplica disponível");

        return node;
    }

    private static Node getNodeRandomForRequest(){
        Random random = new Random();
        Node node = nodes.get(random.nextInt(nodes.size()));
        return node;
    }

}
