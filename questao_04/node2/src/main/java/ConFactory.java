import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConFactory {

    public static Connection getConnectionMySql() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/questao_04",
                "sdlista","sdlista");
    }

    public static Connection getConnectionPostgres() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/questao_04",
                "sdlista","sdlista");
    }
}
