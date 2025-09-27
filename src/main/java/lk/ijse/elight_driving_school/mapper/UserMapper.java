package lk.ijse.elight_driving_school.mapper;

import lk.ijse.elight_driving_school.dto.UserDTO;
import lk.ijse.elight_driving_school.dto.tm.UserTM;
import lk.ijse.elight_driving_school.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .userId(user.getUserId().toString())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUserId(dto.getUserId() == null ? null : Long.parseLong(dto.getUserId()));
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());

        return user;
    }

    public static UserTM toTM(UserDTO dto) {
        if (dto == null) return null;

        UserTM user = new UserTM();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole().name());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());

        return user;
    }
}
