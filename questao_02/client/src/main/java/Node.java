import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Node {
    private InetSocketAddress address;
    private String node;
    private String replicaOf;

    public Node(InetSocketAddress address, String node, String replicaOf) {
        this.address = address;
        this.node = node;
        this.replicaOf = replicaOf;
    }

    @Override
    public String toString() {
        return "Node{" +
                "address=" + address +
                ", node='" + node + '\'' +
                ", replicaOf='" + replicaOf + '\'' +
                '}';
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getReplicaOf() {
        return replicaOf;
    }

    public void setReplicaOf(String replicaOf) {
        this.replicaOf = replicaOf;
    }

    public Node() {
    }
}
