package dj.aurelia.aureliauser.util;

import dj.aurelia.aureliauser.domain.dto.UserDto;
import dj.aurelia.aureliauser.domain.entity.UserEntity;
import dj.aurelia.aureliauser.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserConverterImplTest {


    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserConverterImpl converter;

    private String SALT = "Kr@tos";

    private final UserEntity entity = UserEntity.builder()
            .uuid(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
            .username("test")
            .password("password" + SALT)
            .email("test@test.com")
            .enabled(true)
            .lastPasswordResetDate(Date.from(Instant.ofEpochSecond(1_000_000)))
            .build();
    private final UserDto dto = UserDto.builder()
            .uuid(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
            .username("test")
            .email("test@test.com")
            .enabled(true)
            .build();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void fromEntityToDao_ok() {
        UserDto actual = converter.FromEntityToDto(entity);

        Assert.assertEquals(dto, actual);
    }

    @Test(expected = NullPointerException.class)
    public void fromEntityToDao_nok_null() {
        converter.FromEntityToDto(null);
    }

    @Test
    public void fromDaoToEntity_ok_userEntityExist() {
        Mockito.when(userRepository.findByUsername("test")).thenReturn(Optional.of(entity));
        UserEntity actual = converter.FromDtoToEntity(dto);

        Assert.assertEquals(entity, actual);
    }

    @Test
    public void fromDaoToEntity_ok_userEntityDoesntExist() {
        UserEntity actual = converter.FromDtoToEntity(dto);

        Assert.assertEquals(entity.getUuid(), actual.getUuid());
        Assert.assertEquals(entity.getUsername(), actual.getUsername());
        Assert.assertEquals(entity.getEmail(), actual.getEmail());
        Assert.assertEquals(entity.getEnabled(), actual.getEnabled());
        Assert.assertEquals(null, actual.getPassword());
        Assert.assertEquals(null, actual.getLastPasswordResetDate());
    }

    @Test(expected = NullPointerException.class)
    public void fromDaoToEntity_nok_null(){
        converter.FromDtoToEntity(null);
    }
}
