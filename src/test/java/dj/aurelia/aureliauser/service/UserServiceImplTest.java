package dj.aurelia.aureliauser.service;

import dj.aurelia.aureliauser.config.SaltConfig;
import dj.aurelia.aureliauser.domain.RegisterForm;
import dj.aurelia.aureliauser.domain.dto.UserDto;
import dj.aurelia.aureliauser.domain.entity.UserEntity;
import dj.aurelia.aureliauser.handlerexceptions.FormException;
import dj.aurelia.aureliauser.handlerexceptions.UserNotFoundException;
import dj.aurelia.aureliauser.repository.UserRepository;
import dj.aurelia.aureliauser.util.UserConverterImpl;
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
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Mock
    SaltConfig saltConfig;
    @Mock
    UserRepository userRepository;
    @Mock
    UserConverterImpl userConverter;
    @InjectMocks
    UserServiceImpl userService;

    private UserEntity entity1;
    private UserEntity entity2;
    private UserDto dto1;
    private UserDto dto2;
    private RegisterForm form;
    private String Salt = "Kr@tos";

    @Before
    public void setUp() throws Exception {
        this.entity1 = UserEntity.builder()
                .uuid(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .username("test1")
                .password("password" + Salt)
                .email("test1@test.com")
                .enabled(true)
                .lastPasswordResetDate(Date.from(Instant.ofEpochSecond(1_000_000)))
                .build();
        this.dto1 = UserDto.builder()
                .uuid(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .username("test1")
                .email("test1@test.com")
                .enabled(true)
                .build();
        this.form = RegisterForm.builder()
                .username("test1")
                .password("password")
                .email("test1@test.com")
                .build();
        this.entity2 = UserEntity.builder()
                .uuid(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"))
                .username("test2")
                .password("password" + Salt)
                .email("test2@test.com")
                .enabled(true)
                .lastPasswordResetDate(Date.from(Instant.ofEpochSecond(1_000_000)))
                .build();
        this.dto2 = UserDto.builder()
                .uuid(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"))
                .username("test2")
                .email("test2@test.com")
                .enabled(true)
                .build();
    }

    @Test
    public void createUser_ok() {
        Mockito.when(userRepository.save(Mockito.isA(UserEntity.class))).thenReturn(entity1);
        Mockito.when(saltConfig.getSALT()).thenReturn(Salt);
        Mockito.when(userConverter.FromEntityToDto(Mockito.isA(UserEntity.class))).thenCallRealMethod();

        UserDto actual = userService.createUser(form);

        Assert.assertEquals(dto1, actual);
    }

    @Test(expected = FormException.class)
    public void createUser_nok_formNotFull() {
        RegisterForm wrongForm = RegisterForm.builder()
                .username("test")
                .password("password")
                .email(null)
                .build();

        userService.createUser(wrongForm);
    }

    @Test
    public void findUserByUuid_ok() {
        Mockito.when(userRepository.findById(Mockito.isA(UUID.class))).thenReturn(Optional.of(entity1));
        Mockito.when(userConverter.FromEntityToDto(Mockito.isA(UserEntity.class))).thenCallRealMethod();

        UserDto actual = userService.findUserByUuid(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        Assert.assertEquals(dto1, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void findUserByUuid_nok_uuidDoesntExist() {
        Mockito.when(userRepository.findById(Mockito.isA(UUID.class))).thenReturn(Optional.empty());

        userService.findUserByUuid(UUID.fromString("cccccccc-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
    }

    @Test
    public void findUserByUsername_ok() {
        Mockito.when(userRepository.findByUsername(Mockito.isA(String.class))).thenReturn(Optional.of(entity1));
        Mockito.when(userConverter.FromEntityToDto(Mockito.isA(UserEntity.class))).thenCallRealMethod();

        UserDto actual = userService.findUserByUsername("test1");

        Assert.assertEquals(dto1, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void findUserByUsername_nok_usernameDoesntExist() {
        Mockito.when(userRepository.findByUsername(Mockito.isA(String.class))).thenReturn(Optional.empty());

        userService.findUserByUsername("wrongUsername");
    }

    @Test
    public void findAllUsers_ok() {
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(entity1,entity2));
        Mockito.when(userConverter.FromEntityToDto(Mockito.isA(UserEntity.class))).thenCallRealMethod();

        List<UserDto> actuals = userService.findAllUsers();

        Mockito.verify(userConverter, Mockito.times(2)).FromEntityToDto(Mockito.isA(UserEntity.class));
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(actuals.get(0), dto1);
        Assert.assertEquals(actuals.get(1), dto2);
    }

    @Test
    public void findAllUsers_ok_noneUser() {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserDto> actuals = userService.findAllUsers();

        Mockito.verify(userConverter, Mockito.times(0)).FromEntityToDto(Mockito.isA(UserEntity.class));
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Assert.assertTrue(actuals.isEmpty());
    }

    @Test
    public void deleteUserByUuid_ok() {
        Mockito.when(userRepository.findById(Mockito.isA(UUID.class))).thenReturn(Optional.of(entity1));

        Assert.assertTrue(userService.deleteUserByUuid(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")));
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserByUuid_nok_uuidDoesntExist() {
        Mockito.when(userRepository.findById(Mockito.isA(UUID.class))).thenReturn(Optional.empty());

        userService.deleteUserByUuid(UUID.fromString("cccccccc-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
    }

    @Test
    public void deleteUserByUsername_ok() {
        Mockito.when(userRepository.findByUsername(Mockito.isA(String.class))).thenReturn(Optional.of(entity1));

        Assert.assertTrue(userService.deleteUserByUsername("test1"));
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserByUsername_nok_usernameDoesntExist() {
        Mockito.when(userRepository.findByUsername(Mockito.isA(String.class))).thenReturn(Optional.empty());

        userService.deleteUserByUsername("wrongUsername");
    }
}
