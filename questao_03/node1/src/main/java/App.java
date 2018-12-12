import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //EXEMPLO COM OPERAÇÃO 1
        Request op = new Request(2, 5, "op1");
        Socket socket = new Socket("localhost",12000);
        System.out.println("Enviando operação: " + op.toString());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(op);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Integer result = (Integer) in.readObject();
        System.out.println("O resultado é :" + result);
        socket.close();


//        //EXEMPLO COM OPERAÇÃO 2
//        Request op = new Request(2, 5, "op2");
//        Socket socket = new Socket("localhost",11000);
//        System.out.println("Enviando operação: " + op.toString());
//        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//        out.writeObject(op);
//        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//        Integer result = (Integer) in.readObject();
//        System.out.println("O resultado é :" + result);
//        socket.close();
    }
}
