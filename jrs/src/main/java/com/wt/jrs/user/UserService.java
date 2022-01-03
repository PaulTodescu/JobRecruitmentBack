package com.wt.jrs.user;

import com.wt.jrs.application.ApplicationDTO;
import com.wt.jrs.application.ApplicationService;
import com.wt.jrs.job.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;

@Service
@Transactional
public class UserService {

    private final UserDAO userDAO;
    private final ApplicationService applicationService;

    @Autowired
    public UserService(UserDAO userDAO, @Lazy ApplicationService applicationService) {
        this.userDAO = userDAO;
        this.applicationService = applicationService;
    }

    public void addUser(UserEntity user) throws InvocationTargetException, IllegalAccessException {

        Optional<UserEntity> userOptional = userDAO.findUserEntityByEmail(user.getEmail());
        if (userOptional.isPresent()){
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        // phone number is not set during registration, so set contact method to email
        if (user.getPhoneNumber() == null){
            user.setContactMethod(PreferredContactMethod.EMAIL);
        }
        if (user.getRole() == UserRole.RECRUITER){
            UserEntity recruiter = new RecruiterEntity();
            BeanUtils.copyProperties(recruiter, user);
            userDAO.save(recruiter);

        } else  if (user.getRole() == UserRole.EMPLOYEE){
            UserEntity employee = new EmployeeEntity();
            BeanUtils.copyProperties(employee,user);
            userDAO.save(employee);
        }
    }

    public UserEntity findUserById(Long id){
        return userDAO.findUserEntityById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with id: " + id + " was not found")
        );
    }

    public EmployeeEntity findEmployeeById(Long id){
        UserEntity user = userDAO.findUserEntityById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with id: " + id + " was not found")
        );
        if (user instanceof EmployeeEntity){
            return (EmployeeEntity) user;
        }
        throw new RuntimeException("User with id: " + id + " is not an employee");
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
        if (currentUser instanceof RecruiterEntity){
            RecruiterEntity recruiter = (RecruiterEntity) currentUser;
            return new ArrayList<>(recruiter.getJobs());
        } else {
            throw new RuntimeException("Employees do not have access to recruiter jobs");
        }
    }

    public UserDTO findLoggedInUser() {
        String email = this.findLoggedInUserEmail();
        return this.mapUserToDTO(this.findUserByEmail(email));
    }

    public UserRole findLoggedInUserRole() {
        String currentUserEmail = this.findLoggedInUserEmail();
        UserEntity currentUser = this.findUserByEmail(currentUserEmail);
        return currentUser.getRole();
    }

    public Boolean checkIfLoggedInUserIsRecruiter() {
        String currentUserEmail = this.findLoggedInUserEmail();
        UserEntity currentUser = this.findUserByEmail(currentUserEmail);
        return currentUser.getRole() == UserRole.RECRUITER;
    }

    public Boolean checkIfLoggedInUserIsEmployee() {
        String currentUserEmail = this.findLoggedInUserEmail();
        UserEntity currentUser = this.findUserByEmail(currentUserEmail);
        return currentUser.getRole() == UserRole.EMPLOYEE;
    }

    public UserDTO mapUserToDTO(UserEntity user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setContactMethod(user.getContactMethod());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public void updateUser(UserDTO updatedUser) {
        UserEntity loggedInUser = this.findUserByEmail(this.findLoggedInUserEmail());

        String updatedFirstName = updatedUser.getFirstName();
        String updatedLastName = updatedUser.getLastName();
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
        if (updatedUserPhone != null && !(updatedUserPhone.equals(loggedInUser.getPhoneNumber()))){
            loggedInUser.setPhoneNumber(updatedUserPhone);
        }
        if (updatedContactMethod != null && !(updatedContactMethod.equals(loggedInUser.getContactMethod()))){
            loggedInUser.setContactMethod(updatedContactMethod);
        } else if (updatedContactMethod == null){
            loggedInUser.setContactMethod(PreferredContactMethod.EMAIL);
        }
    }

    public List<ApplicationDTO> getEmployeeApplications(){
        String currentUserEmail = this.findLoggedInUserEmail();
        UserEntity currentUser = this.findUserByEmail(currentUserEmail);
        if (currentUser instanceof EmployeeEntity){
            EmployeeEntity employee = (EmployeeEntity) currentUser;
            return new ArrayList<>(this.applicationService.mapApplicationsToDTOs(List.copyOf(employee.getApplications())));
        }else {
            throw new RuntimeException("Applications can be accessed by employees only");
        }
    }
}
