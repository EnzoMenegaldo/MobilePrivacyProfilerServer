package fr.inria.diverse.mobileprivacyprofilerserver.database.user;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil.DBTools;
import fr.inria.diverse.mobileprivacyprofilerserver.utils.AuthenticationUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataBaseHelper {

    public static final UserDataBaseHelper INSTANCE = new UserDataBaseHelper();

    public final static String DATABASE_FILE = "database/UserDataBase.db";
    private static final String email_file = "database/email.txt";
    public static String DATABASE_URL;

    public final static String SUCCESSFUL_AUTHENTICATION = "Authentification réussie";
    public final static String ALREADY_LOGIN_ANOTHER_APP = "Votre compte est déjà lié à un autre appareil";
    public final static String WRONG_INFORMATION = "Les informations que vous avez rentrées sont erronées";
    public final static String DEVICE_ALREADY_USED = "Cet appareil est déjà lié à un compte";


    public static Dao<User, Integer> userDao;

    private UserDataBaseHelper() { }

    /**
     * Initialize our database and DAOs
     */
    public void initialize() throws Exception {

        try {

            //We have to add '.' to the classpath of the jar to make it work
            File database_file = new File(DATABASE_FILE);

            DATABASE_URL = "jdbc:sqlite:" + DATABASE_FILE;

            JdbcConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);

            if (!database_file.exists()) {
                DBTools.INSTANCE.initializeSQLite(DATABASE_URL);
                databaseInitialisation(connectionSource);
            }

            setupDatabase(connectionSource);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Setup our database and DAOs
     */
    public void setupDatabase(ConnectionSource connectionSource) throws Exception {

        UserDataBaseHelper.userDao = DaoManager.createDao(connectionSource, User.class);

    }

    /**
     * Tables creation
     */
    public void databaseInitialisation(ConnectionSource connectionSource)
            throws Exception {

        TableUtils.createTable(connectionSource, User.class);

    }

    /**
     * @return the id of the last user registered
     */
    public int getLastUserId() {

        try {
            CloseableIterator<User> iterator = userDao.iterator(ResultSet.TYPE_FORWARD_ONLY);
            int tableSize = (int) userDao.countOf();
            User lastUser = null;

            if (tableSize == 0) {
                return 0;
            } else if (tableSize == 1) {
                lastUser = iterator.first();
                iterator.closeQuietly();
                return lastUser.get_id();
            } else {
                while (iterator.hasNext())
                    lastUser = iterator.next();

                iterator.closeQuietly();
                return lastUser.get_id();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param email
     * @return true is the user is registered in the DB
     */
    public boolean isUserRegistered(String email) {

        try {
            QueryBuilder<User, Integer> queryBuilder = userDao.queryBuilder();
            queryBuilder.where().eq(User.USER_EMAIL_XML, email);

            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            return userDao.queryForFirst(preparedQuery) != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Create and add a new user in the DB
     *
     * @param newUser
     */
    public void createUser(User newUser) throws SQLException {
        userDao.create(newUser);
    }

    /**
     * return true if the gmail_$credentials are correct
     *
     * @param username
     * @param password
     * @return
     */
    public String checkCredentials(String username, String password, String device) {

        try {
            QueryBuilder<User, Integer> queryBuilderByUsername = userDao.queryBuilder();
            queryBuilderByUsername.where().eq(User.USER_USERNAME_XML, username);
            //Get the first user who has this username;
            User userByUsername = userDao.queryForFirst(queryBuilderByUsername.prepare());

            QueryBuilder<User, Integer> queryBuilderByDevice = userDao.queryBuilder();
            queryBuilderByDevice.where().eq(User.USER_DEVICE_XML, device);
            //Get the first user who has this device
            User userByDevice = userDao.queryForFirst(queryBuilderByDevice.prepare());

            if (userByUsername == null)
                return WRONG_INFORMATION;
            else if (userByDevice == null) {
                if (!userByUsername.getDevice().equals("")) {
                    //Another user already logged in a device tries to log in another one
                    return ALREADY_LOGIN_ANOTHER_APP;
                }
                //It's the first time the use log in the app. Check password
                else if (AuthenticationUtil.INSTANCE.isExpectedPassword(password.toCharArray(), userByUsername.getSalt(), userByUsername.getPassword())) {
                    userByUsername.setDevice(device);
                    userDao.createOrUpdate(userByUsername);
                    return SUCCESSFUL_AUTHENTICATION;
                } else
                    return WRONG_INFORMATION;
            } else if (userByUsername.equals(userByDevice)) {
                if (AuthenticationUtil.INSTANCE.isExpectedPassword(password.toCharArray(), userByUsername.getSalt(), userByUsername.getPassword()))
                    return SUCCESSFUL_AUTHENTICATION;
                else
                    return WRONG_INFORMATION;
            } else
                return DEVICE_ALREADY_USED;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Check the token exists in the user base
     *
     * @param token
     * @return true if the token exists
     */
    public boolean checkToken(String token) {
        try {
            QueryBuilder<User, Integer> queryBuilderByToken = userDao.queryBuilder();
            queryBuilderByToken.where().eq(User.USER_TOKEN_XML, token);
            User user = userDao.queryForFirst(queryBuilderByToken.prepare());

           return user != null ;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create all the users whose email address is in the file
     */
    public void createUser(){
        BufferedReader br;
        try {
            File file = new File(email_file);
            if(!file.exists())
                file.createNewFile();

            br = new BufferedReader(new FileReader(email_file));

            String email;
            while ((email = br.readLine()) != null) {
                if(!isUserRegistered(email)){
                    createUser(new User(email, getLastUserId() +1));
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Update the current token of the user
     * @param username
     */
    public void updateToken(String username, String token){
        try {
            QueryBuilder<User, Integer> queryBuilderByUsername = userDao.queryBuilder();
            queryBuilderByUsername.where().eq(User.USER_USERNAME_XML, username);
            User user = userDao.queryForFirst(queryBuilderByUsername.prepare());

            user.setToken(token);
            userDao.createOrUpdate(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

