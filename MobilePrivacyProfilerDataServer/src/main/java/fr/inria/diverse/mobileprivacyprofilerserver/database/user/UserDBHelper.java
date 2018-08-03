package fr.inria.diverse.mobileprivacyprofilerserver.database.user;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil.DBConstants;
import fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil.DBTools;
import fr.inria.diverse.mobileprivacyprofilerserver.utils.AuthenticationUtil;
import fr.inria.diverse.mobileprivacyprofilerserver.utils.GmailUtil;

import javax.mail.MessagingException;
import java.io.*;
import java.security.GeneralSecurityException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDBHelper {

    public static final UserDBHelper INSTANCE = new UserDBHelper();

    public final static String SUCCESSFUL_AUTHENTICATION = "Authentification réussie";
    public final static String ALREADY_LOGIN_ANOTHER_APP = "Votre compte est déjà lié à un autre appareil";
    public final static String WRONG_INFORMATION = "Les informations que vous avez rentrées sont erronées";
    public final static String DEVICE_ALREADY_USED = "Cet appareil est déjà lié à un compte";

    public static final String PROFILE_EMAIL = "mobileprofiler.ur1@gmail.com";


    public static Dao<User, Integer> userDao;

    private UserDBHelper() { }

    /**
     * Initialize our database and DAOs
     */
    public void initialize() throws Exception {

        try {

            //We have to add '.' to the classpath of the jar to make it work
            File database_file = new File(DBConstants.USER_DATABASE_FILE_PATH);

            JdbcConnectionSource connectionSource = new JdbcConnectionSource(DBConstants.USER_DATABASE_URL);

            if (!database_file.exists()) {
                DBTools.INSTANCE.initializeSQLite(DBConstants.USER_DATABASE_URL);
                databaseInitialisation(connectionSource);
            }

            setupDatabase(connectionSource);
            setupAdminCredentials();
            createUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Setup our database and DAOs
     */
    public void setupDatabase(ConnectionSource connectionSource) throws Exception {

        UserDBHelper.userDao = DaoManager.createDao(connectionSource, User.class);

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
     * @param email
     * @return true if the user has been correctly stored
     */
    public boolean createUser(String email) throws SQLException {
        if(!isUserRegistered(email)){
            try {
                userDao.create(new User(email, getLastUserId() +1));
                return true;
            } catch (GeneralSecurityException | IOException | MessagingException e) {
                e.printStackTrace();
            }
        }
        return false;
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
     * Check if the given token is the one associated to the admin account
     * @param token
     * @return true whether it's the admin's token else false
     */
    public boolean checkAdminToken(String token){
        try {
            QueryBuilder<User, Integer> queryBuilderByToken = userDao.queryBuilder();
            queryBuilderByToken.where().eq(User.USER_USERNAME_XML, "admin");
            User user = userDao.queryForFirst(queryBuilderByToken.prepare());

            return user.getToken().equals(token);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Create all the users whose email address is in the file
     */
    public void createUsers(){
        BufferedReader br;
        try {
            File file = new File(DBConstants.EMAIL_FILE_PATH);
            if(!file.exists())
                file.createNewFile();

            br = new BufferedReader(new FileReader(DBConstants.EMAIL_FILE_PATH));

            String email;
            while ((email = br.readLine()) != null) {
                if(!isUserRegistered(email)){
                    createUser(email);
                }
            }
            br.close();
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

    public void setupAdminCredentials() throws SQLException {
        if(!isUserRegistered(PROFILE_EMAIL)){
            String username = "admin";
            try {
                userDao.create(new User(username,PROFILE_EMAIL));
            } catch (GeneralSecurityException | IOException | MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveNewEmailUser(String email){
        BufferedWriter bw;
        try {
            File file = new File(DBConstants.EMAIL_FILE_PATH);
            if(!file.exists())
                file.createNewFile();

            bw = new BufferedWriter(new FileWriter(DBConstants.EMAIL_FILE_PATH,true));
            bw.write(email+"\n");

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

