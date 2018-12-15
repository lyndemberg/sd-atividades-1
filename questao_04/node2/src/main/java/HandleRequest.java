import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.SQLException;

public class HandleRequest implements Runnable {


    private final Socket accept;
    private final UserDao userDao;


    public HandleRequest(Socket accept, UserDao userDao) {
        this.accept = accept;
        this.userDao = userDao;
    }

    public void run() {

        try {
            ObjectInputStream in = new ObjectInputStream(accept.getInputStream());
            User post = (User) in.readObject();
            userDao.insertPostgres(post);
            userDao.insertMySql(post);
            accept.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
