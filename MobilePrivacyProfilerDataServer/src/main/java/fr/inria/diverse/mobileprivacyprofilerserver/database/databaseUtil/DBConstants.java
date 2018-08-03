/*  */
package fr.inria.diverse.mobileprivacyprofilerserver.database.databaseUtil;

public class DBConstants {

// Start of user code setup your db informations here :
	// we are using the created SQLite database
	public final static String DATA_FOLDER = "data/";
	public final static String DATABASE_FOLDER = "database/";
	public final static String DATABASE_FILE = "MobilePrivacyProfilerDB.db";
	public final static String DATABASE_FILE_PATH = DATA_FOLDER + DATABASE_FOLDER + DATABASE_FILE ;
	public final static String DATABASE_URL = "jdbc:sqlite:"+ DATABASE_FILE_PATH;

	public final static String USER_DATABASE_FILE = "UserDataBase.db";
	public static final String EMAIL_FILE = "email.txt";
	public final static String USER_DATABASE_FILE_PATH = DATA_FOLDER + DATABASE_FOLDER + USER_DATABASE_FILE;
	public final static String EMAIL_FILE_PATH = DATA_FOLDER + DATABASE_FOLDER + EMAIL_FILE;
	public final static String USER_DATABASE_URL = "jdbc:sqlite:"+ USER_DATABASE_FILE_PATH;
// End of user code	


}

