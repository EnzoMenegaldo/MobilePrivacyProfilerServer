import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;


import java.io.IOException;
import java.security.GeneralSecurityException;

@DatabaseTable(tableName = "user")
public class User {

    private static final String PROFILE_EMAIL = "mobileprofiler.ur1@gmail.com";
    private static int user_number  = 1;
    public static final String USER_USERNAME_PREFIX = "profile_user_";

    public static final String USER_ID_XML = "id";
    public static final String USER_USERNAME_XML = "username";
    public static final String USER_SALT_XML = "salt";
    public static final String USER_PASSWORD_XML = "password";
    public static final String USER_EMAIL_XML = "email";
    public static final String USER_DEVICE_XML = "device";

    // id is generated by the database and set on the object automagically
    @DatabaseField(generatedId = true)
    protected int _id;

    @DatabaseField
    private String username;

    @DatabaseField(dataType=DataType.BYTE_ARRAY)
    private byte[] salt;

    @DatabaseField(dataType=DataType.BYTE_ARRAY)
    private byte[] password;

    @DatabaseField
    private String email;

    @DatabaseField
    private String device;

    public User() {} // needed by ormlite

    public User(String username, byte[] salt, byte[] password, String email) {
        super();
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.email = email;
    }

    public User(String email){
        this.username = USER_USERNAME_PREFIX +user_number;
        this.email = email;
        this.salt = Password.generateSalt();
        final String clearPassword = Password.generateRandomPassword(Password.Length);
        sendPasswordToEmail(clearPassword);
        this.password = Password.hash(clearPassword.toCharArray(),this.salt);
        this.device = "";
        user_number++;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String id) {
        this.username = id;
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

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }


    public void sendPasswordToEmail(String password){
        try {
            GmailUtil.sendMessage(this.email,PROFILE_EMAIL,"Profile : nom d'utilisateur et mot de passe","Bonjour,\n\nVous trouverez ci-dessous votre nom d'utilisateur ainsi que votre mot passe nécessaires lors de votre connection à l'application.\nNom d'utilisateur : "+ getUsername()+"\nMot de passe : "+password+"\n\nL'équipe Profile.");
        } catch (MessagingException | javax.mail.MessagingException | IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean equals(Object obj) {
        return this.username.equals(((User)obj).username) && this.device.equals(((User)obj).device);
    }
}
