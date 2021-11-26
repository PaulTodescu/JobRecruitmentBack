package com.wt.jrs.user;

import com.wt.jrs.job.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addUser(UserEntity user){

        Optional<UserEntity> userOptional = userDAO.findUserEntityByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        userDAO.save(user);
    }

    public UserEntity findUserById(Long id){
        return userDAO.findUserEntityById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with id: " + id + " was not found")
        );
    }

    public UserEntity findUserByEmail(String email){
        return userDAO.findUserEntityByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with email: " + email + " was not found")
        );
    }

    public String findUsernameByEmail(String email){
        UserEntity user = userDAO.findUserEntityByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with email: " + email + " was not found")
        );
        return user.getFirstName();
    }

    public List<UserEntity> findAllUsers(){
        return userDAO.findAll();
    }

    public String findLoggedInUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        return email;
    }

    public String findLoggedInUserName() {
        String email = this.findLoggedInUserEmail();
        Optional<UserEntity> user = userDAO.findUserEntityByEmail(email);
        if(user.isPresent()){
            return user.get().getFirstName();
        } else {
            return "unknown";
        }
    }

    public List<JobEntity> findAllJobsForCurrentUser() {
        String currentUserEmail = this.findLoggedInUserEmail();
        UserEntity currentUser = this.findUserByEmail(currentUserEmail);
        return new ArrayList<>(currentUser.getJobs());
    }

    public UserDTO findLoggedInUser() {
        String email = this.findLoggedInUserEmail();
        return this.mapUserToDTO(this.findUserByEmail(email));
    }

    public UserDTO mapUserToDTO(UserEntity user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setContactMethod(user.getContactMethod());
        return userDTO;
    }

    public void updateUser(UserDTO updatedUser) {
        UserEntity loggedInUser = this.findUserByEmail(this.findLoggedInUserEmail());

        String updatedFirstName = updatedUser.getFirstName();
        String updatedLastName = updatedUser.getLastName();
        UserRole updatedUserRole = updatedUser.getRole();
        String updatedUserPhone = updatedUser.getPhoneNumber();
        PreferredContactMethod updatedContactMethod = updatedUser.getContactMethod();

        if (updatedFirstName != null && !(updatedFirstName.equals(loggedInUser.getFirstName()))){
            loggedInUser.setFirstName(updatedFirstName);
        }
        if (updatedLastName != null && !(updatedLastName.equals(loggedInUser.getLastName()))){
            loggedInUser.setLastName(updatedLastName);
        }
        if (updatedFirstName != null && !(updatedFirstName.equals(loggedInUser.getFirstName()))){
            loggedInUser.setFirstName(updatedFirstName);
        }
        if (updatedUserRole != null && !(updatedUserRole.equals(loggedInUser.getRole()))){
            loggedInUser.setRole(updatedUserRole);
        }
        if (updatedUserRole != null && !(updatedUserRole.equals(loggedInUser.getRole()))){
            loggedInUser.setRole(updatedUserRole);
        }
        if (updatedUserPhone != null && !(updatedUserPhone.equals(loggedInUser.getPhoneNumber()))){
            loggedInUser.setPhoneNumber(updatedUserPhone);
        }
        if (updatedContactMethod != null && !(updatedContactMethod.equals(loggedInUser.getContactMethod()))){
            loggedInUser.setContactMethod(updatedContactMethod);
        }
    }
}