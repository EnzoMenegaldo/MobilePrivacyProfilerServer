package fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fr.inria.diverse.mobileprivacyprofilerserver.database.user.User;
import fr.inria.diverse.mobileprivacyprofilerserver.database.user.UserDataBaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTools {

    public static final DBTools INSTANCE  = new DBTools();

    // Constructeur
    private DBTools(){

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
}


