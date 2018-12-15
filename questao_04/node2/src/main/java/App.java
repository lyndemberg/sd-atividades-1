import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost", 11000));

//     SOLUÇÃO BÁSICA SEM THREAD
//        while(true){
//            Socket accept = server.accept();
//
//            ObjectInputStream in = new ObjectInputStream(accept.getInputStream());
//            User post = (User) in.readObject();
//
//            try {
//                userDao.insertPostgres(post);
//                userDao.insertMySql(post);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//
//            accept.close();
//        }

        // COM THREAD
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        while(true){
            Socket accept = server.accept();
            executorService.execute(new HandleRequest(accept,userDao));
        }


//        server.close();


    }
}
