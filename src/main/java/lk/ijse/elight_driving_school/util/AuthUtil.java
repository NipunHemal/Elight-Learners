package lk.ijse.elight_driving_school.util;


import lk.ijse.elight_driving_school.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

public class AuthUtil {
    @Getter
    @Setter
    private static UserDTO currentUser;

    public static String getRole() {
        return currentUser != null ? currentUser.getRole().name() : null;
    }

    public static void clear() {
        currentUser = null;
    }
}