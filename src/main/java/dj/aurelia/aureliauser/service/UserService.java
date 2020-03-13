package dj.aurelia.aureliauser.service;

import dj.aurelia.aureliauser.domain.RegisterForm;
import dj.aurelia.aureliauser.domain.dto.UserDto;
import dj.aurelia.aureliauser.domain.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto createUser(RegisterForm form);
    UserDto findUserByUuid(UUID uuid);
    UserDto findUserByUsername(String username);
    List<UserDto> findAllUsers();
    boolean deleteUserByUuid(UUID uuid);
    boolean deleteUserByUsername(String username);

    UserEntity findUserbyUsernameSecurity(String username);
}
