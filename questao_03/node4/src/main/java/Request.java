import java.io.Serializable;

public class Request implements Serializable {
    private int numero1;
    private int numero2;
    private String op;

    public Request(int numero1, int numero2, String op) {
        this.numero1 = numero1;
        this.numero2 = numero2;
        this.op = op;
    }

    @Override
    public String toString() {
        return "Request{" +
                "numero1=" + numero1 +
                ", numero2=" + numero2 +
                ", op='" + op + '\'' +
                '}';
    }

    public int getNumero1() {
        return numero1;
    }

    public void setNumero1(int numero1) {
        this.numero1 = numero1;
    }

    public int getNumero2() {
        return numero2;
    }

    public void setNumero2(int numero2) {
        this.numero2 = numero2;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
