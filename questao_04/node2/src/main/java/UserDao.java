
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    public void insertPostgres(User user) throws SQLException {
        Connection con = null;
        try{
            con = ConFactory.getConnectionPostgres();
            String sql = "INSERT INTO tb_user (nome) VALUES (?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, user.getNome());
            statement.executeUpdate();
        }finally {
            if(con != null)
                con.close();
        }
    }

    public void insertMySql(User user) throws SQLException {
        Connection con = null;
        try{
            con = ConFactory.getConnectionMySql();
            String sql = "INSERT INTO tb_user (nome) VALUES (?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, user.getNome());
            statement.executeUpdate();
        }finally {
            if(con != null)
                con.close();
        }
    }
}
