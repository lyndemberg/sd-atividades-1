import java.io.Serializable;

public class Request implements Serializable {
    public String op;
    public int x;
    public int y;

    public Request(String op, int x, int y) {
        this.op = op;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Request{" +
                "op='" + op + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
