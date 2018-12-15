
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {

    public void insertPostgres(User user) throws SQLException {
        Connection con = null;
        try{
            con = ConFactory.getConnectionPostgres();
            String sql = "INSERT INTO tb_user (code,nome) VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, user.getCode());
            statement.setString(2, user.getNome());
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
            String sql = "INSERT INTO tb_user (code,nome) VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, user.getCode());
            statement.setString(2, user.getNome());
            statement.executeUpdate();
        }finally {
            if(con != null)
                con.close();
        }
    }
}
