package lk.ijse.elight_driving_school.service.custom.impl;

import lk.ijse.elight_driving_school.enums.DAOTypes;
import lk.ijse.elight_driving_school.mapper.UserMapper;
import lk.ijse.elight_driving_school.service.custom.UserService;
import lk.ijse.elight_driving_school.service.exception.DuplicateException;
import lk.ijse.elight_driving_school.service.exception.NotFoundException;
import lk.ijse.elight_driving_school.dao.DAOFactory;
import lk.ijse.elight_driving_school.dao.custom.UserDAO;
import lk.ijse.elight_driving_school.dto.UserDTO;
import lk.ijse.elight_driving_school.entity.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private  final UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOTypes.USER);

    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        List<User> userList = userDAO.getAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            userDTOList.add(UserMapper.toDTO(user));
        }
        return userDTOList;
    }

    @Override
    public boolean saveUsers(UserDTO t) throws Exception {
        return userDAO.save(UserMapper.toEntity(t));
    }

    @Override
    public boolean updateUsers(UserDTO t) throws Exception {
        Optional<User> user = userDAO.findById(t.getUserId());
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return userDAO.update(UserMapper.toEntity(t));
    }

    @Override
    public boolean deleteUsers(String id) throws Exception {
        Optional<User> user = userDAO.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return userDAO.delete(id);
    }

    @Override
    public List<String> getAllUserIds() throws Exception {
        return userDAO.getAllIds();
    }

    @Override
    public Optional<UserDTO> findByUserId(String id) throws Exception {
        Optional<User> user = userDAO.findById(id);
        return user.map(UserMapper::toDTO);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userDAO.getUserByEmail(email);
        return UserMapper.toDTO(user);
    }
}
