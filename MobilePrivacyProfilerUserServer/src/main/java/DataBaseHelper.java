import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

public class DataBaseHelper {

    public static final DataBaseHelper INSTANCE = new DataBaseHelper();

    public final static String DATABASE_URL = "jdbc:sqlite:run/database/UserDataBase.db";


    public static Dao<User, Integer> userDao;

    private DataBaseHelper(){

        DBTools dBTools = new DBTools();
        try {
            File file = new File ("./run/database/UserDataBase.db");

            JdbcConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);

            if(!file.exists()){
                dBTools.initializeSQLite(DATABASE_URL);
                dBTools.databaseInitialisation(connectionSource);
            }

            dBTools.setupDatabase(connectionSource);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param email
     * @return true is the user is registered in the DB
     */
    public static boolean isUserRegistered(String email) {

        try {
            QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();
            queryBuilder.where().eq(User.USER_EMAIL_XML,email);

            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            return userDao.queryForFirst(preparedQuery) != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Create and add a new user in the DB
     * @param newUser
     */
    public static void createUser(User newUser) throws SQLException {
        userDao.create(newUser);
    }

    /**
     * return true if the credentials are correct
     * @param username
     * @param password
     * @return
     */
    public static boolean checkCredentials(String username, String password) {

        try {
            QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();
            queryBuilder.where().eq(User.USER_ID_XML,username);

            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            User user = userDao.queryForFirst(preparedQuery);

            if(user != null)
                return Password.isExpectedPassword(password.toCharArray(),user.getSalt(),user.getPassword());

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

