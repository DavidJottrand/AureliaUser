package dj.aurelia.aureliauser.service;

import dj.aurelia.aureliauser.domain.RegisterForm;
import dj.aurelia.aureliauser.domain.dto.UserDto;
import dj.aurelia.aureliauser.domain.entity.UserEntity;
import dj.aurelia.aureliauser.handlerexceptions.FormException;
import dj.aurelia.aureliauser.handlerexceptions.UserNotFoundException;
import dj.aurelia.aureliauser.repository.UserRepository;
import dj.aurelia.aureliauser.util.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private UserConverter userConverter;

    private List<Long> roles = new ArrayList<>();

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        roles.add(2L);
    }

    @Override
    public UserDto createUser(RegisterForm form) {
        if (!form.check()){
            throw new FormException("RegisterForm not available.");
        }
        else {
            UserEntity userEntity = UserEntity.builder()
                    .uuid(UUID.randomUUID())
                    .username(form.getUsername())
                    .password(new BCryptPasswordEncoder().encode(form.getPassword()))
                    .email(form.getEmail())
                    .lastPasswordResetDate(Date.from(Instant.now()))
                    .enabled(true)
                    .authorities(roles)
                    .build();
            userEntity = userRepository.save(userEntity);
            return userConverter.FromEntityToDto(userEntity);
        }
    }

    @Override
    public UserDto findUserByUuid(UUID uuid) {
        Optional<UserEntity> opt = userRepository.findById(uuid);
        if (opt.isPresent()) {
            return userConverter.FromEntityToDto(opt.get());
        } else {
            throw new UserNotFoundException("None user found with the uuid : " + uuid);
        }
    }


    @Override
    public UserDto findUserByUsername(String username) {
        Optional<UserEntity> opt = userRepository.findByUsername(username);
        if (opt.isPresent()){
            return userConverter.FromEntityToDto(opt.get());
        }
        else{
            throw new UserNotFoundException("None user found with the username : " + username);
        }
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserDto> dtos = new ArrayList<>();
        List<UserEntity> entities = userRepository.findAll();
        if (entities.isEmpty()){
            return dtos;
        }
        else{
            for (UserEntity entity : entities) {
                dtos.add(userConverter.FromEntityToDto(entity));
            }
            return dtos;
        }
    }

    @Override
    public boolean deleteUserByUuid(UUID uuid) {
        Optional<UserEntity> opt = userRepository.findById(uuid);
        if (opt.isPresent()){
            userRepository.delete(opt.get());
            return true;
        }
        else{
            throw new UserNotFoundException("None user found with the uuid : " + uuid);
        }
    }

    @Override
    public boolean deleteUserByUsername(String username) {
        Optional<UserEntity> opt = userRepository.findByUsername(username);
        if (opt.isPresent()){
            userRepository.delete(opt.get());
            return true;
        }
        else{
            throw new UserNotFoundException("None user found with the username : " + username);
        }
    }

    @Override
    public UserEntity findUserbyUsernameSecurity(String username) {
        Optional<UserEntity> opt = userRepository.findByUsername(username);
        if(opt.isPresent()){
            return opt.get();
        }
        else {
            throw new UserNotFoundException("None user found with the username : " + username);
        }
    }
}
