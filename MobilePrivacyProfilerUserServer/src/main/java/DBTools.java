import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTools {

    // Constructeur
    public DBTools(){

    }

    public void initializeSQLite(String url) throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection(url);

        Statement stmt = c.createStatement();
        String sql = "CREATE TABLE \"android_metadata\" (\"locale\" TEXT DEFAULT 'en_US')";
        stmt.executeUpdate(sql);
        stmt.close();

        stmt = c.createStatement();
        sql = "    INSERT INTO \"android_metadata\" VALUES ('en_US')";
        stmt.executeUpdate(sql);
        stmt.close();
        c.close();

    }

    /**
     * Setup our database and DAOs
     */
    public void setupDatabase(ConnectionSource connectionSource)
            throws Exception {

        DataBaseHelper.userDao = DaoManager.createDao(connectionSource, User.class);

    }

    /**
     *  Tables creation
     */
    public void databaseInitialisation(ConnectionSource connectionSource)
            throws Exception {

        TableUtils.createTable(connectionSource, User.class);

    }

}


