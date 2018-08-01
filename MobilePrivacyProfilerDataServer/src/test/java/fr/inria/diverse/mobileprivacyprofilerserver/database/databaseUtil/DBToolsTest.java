package fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import fr.inria.diverse.mobileprivacyprofilerserver.database.user.UserDBHelperTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.*;

import static org.junit.Assert.*;

public class DBToolsTest {

    public final static String DATABASE_FILE = "database/UserDataBaseTest.db";
    public static String DATABASE_URL;
    static JdbcConnectionSource connectionSource ;
    static File database_file;

    @Before
    public void setup(){

        String database_path = UserDBHelperTest.class.getClassLoader().getResource(DATABASE_FILE).getPath();
        database_file = new File(database_path);

        if(database_file.exists()){
            try {
                database_file.delete();
                database_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        DATABASE_URL = "jdbc:sqlite:" + database_path;
    }

    @Test
    public void initializeSQLite() {
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            DBTools.INSTANCE.initializeSQLite(DATABASE_URL);

            Connection c = DriverManager.getConnection(DATABASE_URL);

            DatabaseMetaData md = c.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);

            rs.next();
            assertEquals(rs.getString(3),"android_metadata");

            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}