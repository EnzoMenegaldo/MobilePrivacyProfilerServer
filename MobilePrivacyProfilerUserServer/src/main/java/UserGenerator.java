import java.io.*;
import java.sql.SQLException;

public class UserGenerator {

    public static final int MAX_USER = 10;
    public static final UserGenerator INSTANCE = new UserGenerator();
    public static final String email_file = "./run/database/email.txt";

    private DataBaseHelper helper;

    private UserGenerator(){
        helper = DataBaseHelper.INSTANCE;
    }

    /**
     * Create all the users whose email address is in the file
     * @param file
     */
    public void createUser(String file){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String email;
            while ((email = br.readLine()) != null) {
                if(!DataBaseHelper.isUserRegistered(email)){
                    DataBaseHelper.createUser(new User(email, DataBaseHelper.getLastUserId() +1));
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

}