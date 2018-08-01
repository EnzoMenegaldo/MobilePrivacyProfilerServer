package fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil;

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


