import java.io.Serializable;

public class User implements Serializable {
    private int code;
    private String nome;

    @Override
    public String toString() {
        return "User{" +
                "code=" + code +
                ", nome='" + nome + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public User() {
    }

    public User(String nome) {
        this.nome = nome;
    }
}
