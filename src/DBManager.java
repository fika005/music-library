package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    public static void main(String[] args) {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/firoozehkaveh/Programming/project-3-music-player-fika005/src/music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(38);
            String songName = new String();
            statement.executeUpdate("insert into songs values (2, 'something',1,1);");
            statement.executeUpdate("insert into users values (3, 'Joe','cat');");
            ResultSet rs = statement.executeQuery("select * from songs");
            while (rs.next()) {
                System.out.println(" name = " + rs.getString("name"));
                System.out.println(" id = " + rs.getInt("id"));
                //String pw = rs.getString("password");

            }

        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
