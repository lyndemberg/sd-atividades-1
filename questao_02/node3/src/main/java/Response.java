import java.io.Serializable;

public class Response implements Serializable {

    private int result;
    private boolean solved;

    public Response() {
    }

    public Response(int result, boolean solved) {
        this.result = result;
        this.solved = solved;
    }

    @Override
    public String toString() {
        return "Response{" +
                "result=" + result +
                ", solved=" + solved +
                '}';
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
