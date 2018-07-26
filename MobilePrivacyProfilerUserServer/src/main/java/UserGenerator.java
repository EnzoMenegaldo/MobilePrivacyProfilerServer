import java.io.*;
import java.sql.SQLException;

public class UserGenerator {

    public static final UserGenerator INSTANCE = new UserGenerator();
    private static final String email_file = "database/email.txt";

    private DataBaseHelper helper;

    private UserGenerator(){

        helper = DataBaseHelper.INSTANCE;
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
                if(!DataBaseHelper.isUserRegistered(email)){
                    DataBaseHelper.createUser(new User(email, DataBaseHelper.getLastUserId() +1));
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

}