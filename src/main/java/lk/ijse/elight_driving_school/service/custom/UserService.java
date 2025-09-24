package lk.ijse.elight_driving_school.service.custom;

import lk.ijse.elight_driving_school.dto.UserDTO;
import lk.ijse.elight_driving_school.service.SuperService;

import java.util.List;
import java.util.Optional;

public interface UserService extends SuperService {
    List<UserDTO> getAllUsers() throws Exception;

    String getLastUserId() throws Exception;

    boolean saveUsers(UserDTO t) throws Exception;

    boolean updateUsers(UserDTO t) throws Exception;

    boolean deleteUsers(String id) throws Exception;

    List<String> getAllUserIds() throws Exception;

    Optional<UserDTO> findByUserId(String id) throws Exception;

    String generateNextUserId();

    public UserDTO getUserByEmail(String email) ;
}
