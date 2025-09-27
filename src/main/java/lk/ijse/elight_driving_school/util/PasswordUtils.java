package lk.ijse.elight_driving_school.util;

import org.mindrot.jbcrypt.BCrypt;

public  class PasswordUtils {

    public static String hashPassword(String password) {
        return  BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return password.equals(hashedPassword);
//        return BCrypt.checkpw(password, hashedPassword);
    }
}
