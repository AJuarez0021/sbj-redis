package com.work.crud.service;

import com.work.crud.dto.UserDTO;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author linux
 */
public interface UserService {
    UserDTO saveUser(UserDTO user);
    UserDTO updateUser(String id, UserDTO user);
    Optional<UserDTO> getUserById(String id);
    List<UserDTO> getAllUsers();
    void deleteUser(String id);
    boolean userExists(String id);
}
