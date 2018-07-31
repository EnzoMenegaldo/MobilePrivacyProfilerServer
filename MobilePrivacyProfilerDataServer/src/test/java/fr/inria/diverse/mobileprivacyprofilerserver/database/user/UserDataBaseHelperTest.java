package fr.inria.diverse.mobileprivacyprofilerserver.database.user;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil.DBTools;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDataBaseHelperTest {

    public final static String DATABASE_FILE = "database/UserDataBaseTest.db";
    public static String DATABASE_URL;
    static JdbcConnectionSource connectionSource ;
    static File database_file;


    @Before
    public void setup(){

        String database_path = UserDataBaseHelperTest.class.getClassLoader().getResource(DATABASE_FILE).getPath();
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
    public void initialize() {

    }

    @Test
    public void databaseInitialisation() {
        try {

            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            DBTools.INSTANCE.initializeSQLite(DATABASE_URL);

            UserDataBaseHelper.INSTANCE.databaseInitialisation(connectionSource);
            assertTrue(UserDataBaseHelper.userDao.isTableExists());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void setupDatabase() {
        try {

            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            DBTools.INSTANCE.initializeSQLite(DATABASE_URL);

            UserDataBaseHelper.INSTANCE.databaseInitialisation(connectionSource);

            UserDataBaseHelper.INSTANCE.setupDatabase(connectionSource);
            assertNotEquals(UserDataBaseHelper.userDao,null);

        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    public void getLastUserId() {
        /*try {

            UserDataBaseHelper.INSTANCE.initialize();
            assertEquals(UserDataBaseHelper.INSTANCE.getLastUserId(),0);
            User u1 = new User("u1",1);
            User u2 = new User("u2",2);
            User u3 = new User("u3",3);

            UserDataBaseHelper.userDao.createIfNotExists(u1);
            UserDataBaseHelper.userDao.createIfNotExists(u2);
            UserDataBaseHelper.userDao.createIfNotExists(u3);

            UserDataBaseHelper.userDao.delete(u2);
            assertEquals(UserDataBaseHelper.INSTANCE.getLastUserId(),3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    @Test
    public void isUserRegistered() {
    }

    @Test
    public void createUser() {
    }

    @Test
    public void checkCredentials() {
    }

    @Test
    public void checkToken() {
    }

}