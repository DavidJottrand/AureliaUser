package dj.aurelia.aureliauser.controller;

import dj.aurelia.aureliauser.domain.RegisterForm;
import dj.aurelia.aureliauser.domain.dto.UserDto;
import dj.aurelia.aureliauser.domain.entity.UserEntity;
import dj.aurelia.aureliauser.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${server.port}")
    private int port;
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username/security/{username}")
    public UserEntity findUserbyUsernameSecurity(@PathVariable("username")String username) {
        return userService.findUserbyUsernameSecurity(username);
    }

    @PostMapping
    public UserDto createUser(@RequestBody RegisterForm form, @RequestHeader("x-location") String location){
        return userService.createUser(form);
    }

    @GetMapping("/uuid/{uuid}")
    public UserDto findUserByUuid(@PathVariable("uuid")UUID uuid){
        return userService.findUserByUuid(uuid);
    }

    @GetMapping("/username/{username}")
    public UserDto findUserByUsername(@PathVariable("username")String username){
        return userService.findUserByUsername(username);
    }

    @GetMapping
    public List<UserDto> findAllUsers(){
        return userService.findAllUsers();
    }

    @DeleteMapping("/uuid/{uuid}")
    public boolean deleteUserByUuid(@PathVariable("uuid")UUID uuid){
        return userService.deleteUserByUuid(uuid);
    }

    @DeleteMapping("/username/{username}")
    public boolean deleteUserByUuid(@PathVariable("username")String username){
        return userService.deleteUserByUsername(username);
    }
}
