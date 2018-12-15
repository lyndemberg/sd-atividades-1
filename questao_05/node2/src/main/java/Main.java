import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",11000));

        while(true){
            Socket accept = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(accept.getInputStream());
            Request request = (Request) in.readObject();

            if(request.op.equals("diff")){
                executeDiff(request);
            }

            accept.close();
        }


    }

    private static void executeDiff(Request r) throws IOException {
        File diff_file = new File("/home/lyndemberg/diff.txt");
        if(diff_file.exists()){
            int result = r.x - r.y;
            FileWriter fr = new FileWriter(diff_file,true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            pr.println(result);
            br.close();
            fr.close();
        }else {
            System.out.println("Arquivo diff.txt não existe");
        }
        System.out.println(r.toString());
        System.out.println("Node2 escreveu o resultado em diff.txt");
    }
}
