package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean check(String raw, String hashed) {
        return BCrypt.checkpw(raw, hashed);
    }
}
