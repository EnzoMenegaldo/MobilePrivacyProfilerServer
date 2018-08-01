package fr.inria.diverse.mobileprivacyprofilerserver.database.user;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil.DBTools;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDBHelperTest {

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
    public void initialize() {

    }

    @Test
    public void databaseInitialisation() {
        try {

            connectionSource = new JdbcConnectionSource(DATABASE_URL);
            DBTools.INSTANCE.initializeSQLite(DATABASE_URL);

            UserDBHelper.INSTANCE.databaseInitialisation(connectionSource);
            assertTrue(UserDBHelper.userDao.isTableExists());
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

            UserDBHelper.INSTANCE.databaseInitialisation(connectionSource);

            UserDBHelper.INSTANCE.setupDatabase(connectionSource);
            assertNotEquals(UserDBHelper.userDao,null);

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

            UserDBHelper.INSTANCE.initialize();
            assertEquals(UserDBHelper.INSTANCE.getLastUserId(),0);
            User u1 = new User("u1",1);
            User u2 = new User("u2",2);
            User u3 = new User("u3",3);

            UserDBHelper.userDao.createIfNotExists(u1);
            UserDBHelper.userDao.createIfNotExists(u2);
            UserDBHelper.userDao.createIfNotExists(u3);

            UserDBHelper.userDao.delete(u2);
            assertEquals(UserDBHelper.INSTANCE.getLastUserId(),3);
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