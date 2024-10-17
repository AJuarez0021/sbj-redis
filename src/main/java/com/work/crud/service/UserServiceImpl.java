package com.work.crud.service;

import com.work.crud.dto.UserDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author linux
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String KEY = "users";

    @Autowired
    private RedisOperationsService redisService;

    // Crear o actualizar un usuario
    @Override
    public UserDTO saveUser(UserDTO user) {
        redisService.setValue(KEY, user, user.getId());

        return getUserById(user.getId()).get();
    }

    @Override
    public UserDTO updateUser(String id, UserDTO user) {
        Optional<UserDTO> exist = getUserById(id);;
        if (exist.isPresent()) {
            UserDTO existingUser = exist.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            // Guardar el usuario actualizado
            return saveUser(existingUser);
        }
        return null;
    }

    // Leer un usuario por ID
    @Override
    public Optional<UserDTO> getUserById(String id) {
        if (!userExists(id)) {
            return Optional.empty();
        }
        UserDTO user = (UserDTO) redisService.getValue(KEY, id);
        return Optional.of(user);
    }

    // Leer todos los usuarios
    @Override
    public List<UserDTO> getAllUsers() {
        Map<Object, Object> usersMap = redisService.getAllValues(KEY);
        log.info("Map: {}", usersMap);

        return usersMap.values().stream()
                .map(user -> (UserDTO) user) // Convertir cada objeto a User
                .collect(Collectors.toList());  // Recoger todos en una lista
    }

    // Eliminar un usuario
    @Override
    public void deleteUser(String id) {
        redisService.delete(KEY, id);
    }

    // Verificar si un usuario existe
    @Override
    public boolean userExists(String id) {
        return redisService.exist(KEY, id);
    }

}
