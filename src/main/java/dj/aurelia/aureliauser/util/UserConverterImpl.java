package dj.aurelia.aureliauser.util;

import dj.aurelia.aureliauser.domain.dto.UserDto;
import dj.aurelia.aureliauser.domain.entity.UserEntity;
import dj.aurelia.aureliauser.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserConverterImpl implements UserConverter {

    private UserRepository userRepository;

    public UserConverterImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto FromEntityToDto(UserEntity entity) {
        return UserDto.builder()
                .uuid(entity.getUuid())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .enabled(entity.getEnabled())
                .characters(entity.getCharacters())
                .build();
    }

    @Override
    public UserEntity FromDtoToEntity(UserDto dto) {
        Optional<UserEntity> opt = userRepository.findByUsername(dto.getUsername());
        if (opt.isPresent()){
            String password = opt.get().getPassword();
            Date date = opt.get().getLastPasswordResetDate();
            return UserEntity.builder()
                    .uuid(dto.getUuid())
                    .username(dto.getUsername())
                    .password(password)
                    .email(dto.getEmail())
                    .enabled(dto.getEnabled())
                    .lastPasswordResetDate(date)
                    .characters(dto.getCharacters())
                    .build();
        }
        else {
            return UserEntity.builder()
                    .uuid(dto.getUuid())
                    .username(dto.getUsername())
                    .email(dto.getEmail())
                    .enabled(dto.getEnabled())
                    .characters(dto.getCharacters())
                    .build();
        }
    }
}
