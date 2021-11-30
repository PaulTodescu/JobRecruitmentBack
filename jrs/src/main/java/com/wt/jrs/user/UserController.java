package com.wt.jrs.user;

import com.wt.jrs.job.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserEntity> adduser(@RequestBody UserEntity user) throws InvocationTargetException, IllegalAccessException {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        List<UserEntity> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("userId") Long userId){
        UserEntity user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping()
    @ResponseBody
    public String getBeforeLoggedInUsername(@RequestParam String email){
        return userService.findUsernameByEmail(email);
    }

    @GetMapping(path = "/name")
    @ResponseBody
    public String getLoggedInUserName(){
        return userService.findLoggedInUserName();
    }

    @GetMapping(path = "/jobs")
    public ResponseEntity<List<JobEntity>> getJobsForCurrentUser(){
        List<JobEntity> jobs = userService.findAllJobsForCurrentUser();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping(path = "/info")
    public ResponseEntity<UserDTO> getLoggedInUser(){
        UserDTO user = this.userService.findLoggedInUser();
        System.out.println(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDTO> editLoggedInUser(@RequestBody UserDTO user){
        this.userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/role")
    public ResponseEntity<String> getLoggedInUserRole(){
        UserRole role =  userService.findLoggedInUserRole();
        return new ResponseEntity<>(role.toString(), HttpStatus.OK);
    }

    @GetMapping(path = "/role/recruiter")
    public ResponseEntity<Boolean> checkIfLoggedInUserIsRecruiter(){
        Boolean isRecruiter = userService.checkIfLoggedInUserIsRecruiter();
        return new ResponseEntity<Boolean>(isRecruiter, HttpStatus.OK);
    }

    @GetMapping(path = "/role/employee")
    public ResponseEntity<Boolean> checkIfLoggedInUserIsEmployee(){
        Boolean isEmployee = userService.checkIfLoggedInUserIsEmployee();
        return new ResponseEntity<Boolean>(isEmployee, HttpStatus.OK);
    }

}
