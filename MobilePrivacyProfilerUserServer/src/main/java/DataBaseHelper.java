import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DataBaseHelper {

    public static final DataBaseHelper INSTANCE = new DataBaseHelper();

    public final static String DATABASE_URL = "jdbc:sqlite:run/database/UserDataBase.db";

    public final static String SUCCESSFUL_AUTHENTICATION = "Authentification réussie";
    public final static String ALREADY_LOGIN_ANOTHER_APP = "Votre compte est déjà lié à un autre appareil";
    public final static String WRONG_INFORMATION = "Les informations que vous avez rentrées sont erronées";
    public final static String DEVICE_ALREADY_USED = "Cet appareil est déjà lié à un compte";


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
     * @return the id of the last user registered
     */
    public static int getLastUserId() {

        try {
            CloseableIterator<User> iterator = userDao.iterator(ResultSet.TYPE_FORWARD_ONLY);
            int tableSize = (int)userDao.countOf();
            User lastUser = null;

            if(tableSize == 0){
                return 0;
            }else if (tableSize == 1){
                lastUser =  iterator.first();
                iterator.closeQuietly();
                return lastUser.get_id();
            }else{
                while (iterator.hasNext())
                    lastUser =  iterator.next();

                iterator.closeQuietly();
                return lastUser.get_id();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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
    public static String checkCredentials(String username, String password, String device) {

        try {
            QueryBuilder<User, Integer> queryBuilderByUsername = userDao.queryBuilder();
            queryBuilderByUsername.where().eq(User.USER_USERNAME_XML,username);
            //Get the first user who has this username;
            User userByUsername = userDao.queryForFirst( queryBuilderByUsername.prepare());

            QueryBuilder<User, Integer> queryBuilderByDevice = userDao.queryBuilder();
            queryBuilderByDevice.where().eq(User.USER_DEVICE_XML,device);
            //Get the first user who has this device
            User userByDevice = userDao.queryForFirst(queryBuilderByDevice.prepare());

            if(userByUsername == null)
                return WRONG_INFORMATION;
            else if(userByDevice == null){
                if(!userByUsername.getDevice().equals("")){
                    //Another user already logged in a device tries to log in another one
                    return ALREADY_LOGIN_ANOTHER_APP;
                }
                //It's the first time the use log in the app. Check password
                else if(Password.isExpectedPassword(password.toCharArray(), userByUsername.getSalt(), userByUsername.getPassword())) {
                    userByUsername.setDevice(device);
                    userDao.createOrUpdate(userByUsername);
                    return SUCCESSFUL_AUTHENTICATION;
                }else
                    return WRONG_INFORMATION;
            }
            else if(userByUsername.equals(userByDevice)){
                if(Password.isExpectedPassword(password.toCharArray(), userByUsername.getSalt(), userByUsername.getPassword()))
                    return SUCCESSFUL_AUTHENTICATION;
                else
                    return WRONG_INFORMATION;
            }else
                return DEVICE_ALREADY_USED;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}

