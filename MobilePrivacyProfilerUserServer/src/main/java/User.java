import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;


import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.GeneralSecurityException;

@DatabaseTable(tableName = "user")
public class User {

    private static final String PROFILE_EMAIL = "mobileprofiler.ur1@gmail.com";
    private static int user_number  = 1;
    public static final String USER_ID_PREFIX = "profile_user_";

    public static final String USER_ID_XML = "user_id";
    public static final String USER_SALT_XML = "salt";
    public static final String USER_PASSWORD_XML = "password";
    public static final String USER_EMAIL_XML = "email";

    @DatabaseField
    private String user_id;

    @DatabaseField(dataType=DataType.BYTE_ARRAY)
    private byte[] salt;

    @DatabaseField(dataType=DataType.BYTE_ARRAY)
    private byte[] password;

    @DatabaseField
    private String email;


    public User() {} // needed by ormlite

    public User(String user_id, byte[] salt, byte[] password, String email) {
        super();
        this.user_id = user_id;
        this.salt = salt;
        this.password = password;
        this.email = email;
    }

    public User(String email){
        this.user_id = USER_ID_PREFIX+user_number;
        this.email = email;
        this.salt = Password.generateSalt();
        final String clearPassword = Password.generateRandomPassword(Password.Length);
        sendPasswordToEmail(clearPassword);
        this.password = Password.hash(clearPassword.toCharArray(),this.salt);
        user_number++;
    }


    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String id) {
        this.user_id = id;
    }

    public byte[] getSalt() {
        return salt;
    }
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getPassword() {
        return password;
    }
    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }



    public void sendPasswordToEmail(String password){
        try {
            GmailUtil.sendMessage(this.email,PROFILE_EMAIL,"Profile : nom d'utilisateur et mot de passe","Bonjour,\n\nVous trouverez ci-dessous votre nom d'utilisateur ainsi que votre mot passe nécessaires lors de votre connection à l'application.\nNom d'utilisateur : "+getUser_id()+"\nMot de passe : "+password+"\n\nL'équipe Profile.");
        } catch (MessagingException | javax.mail.MessagingException | IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }

    }
}
