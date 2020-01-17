package dj.aurelia.aureliauser.util;

import dj.aurelia.aureliauser.domain.dto.UserDto;
import dj.aurelia.aureliauser.domain.entity.UserEntity;

public interface UserConverter {

    UserDto FromEntityToDto(UserEntity entity);
    UserEntity FromDtoToEntity(UserDto dto);

}
