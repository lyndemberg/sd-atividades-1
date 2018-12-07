import java.net.SocketAddress;

public class Node {
    private SocketAddress address;
    private String description;

    public Node(SocketAddress address, String description) {
        this.address = address;
        this.description = description;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Node{" +
                "address=" + address +
                ", description='" + description + '\'' +
                '}';
    }
}
