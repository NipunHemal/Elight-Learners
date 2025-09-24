package lk.ijse.elight_driving_school.dto;

import lk.ijse.elight_driving_school.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String userId;
    private String username;
    private String password;
    private Role role;
    private String email;
    private String status;
}
