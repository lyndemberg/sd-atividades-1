import java.net.SocketAddress;

public class Node {
    private SocketAddress address;
    private String node;
    private String replicaOf;

    public Node(SocketAddress address, String node, String replicaOf) {
        this.address = address;
        this.node = node;
        this.replicaOf = replicaOf;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
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

    @Override
    public String toString() {
        return "Node{" +
                "address=" + address +
                ", node='" + node + '\'' +
                ", replicaOf='" + replicaOf + '\'' +
                '}';
    }
}
