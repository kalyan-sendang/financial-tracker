package com.project.financialtracker.service.userService;

import com.project.financialtracker.model.user.User;
import com.project.financialtracker.model.user.UserDto;
import com.project.financialtracker.repository.userrepo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<UserDto> getAllUser(){
        List<User>users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDto(user.getUserId(),user.getUserName(), user.getEmail(), user.getProfession()))
                .toList();
    }

    public User getAUserById(Integer id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.orElse(null);
    }

    public User getUserByUserName(String userName){
        Optional<User> optionalUser = userRepository.findUserByUserName(userName);
        return optionalUser.orElse(null);
    }

    public UserDto registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User newUser = userRepository.save(user);
        return new UserDto(newUser.getUserId(), newUser.getUserName(), newUser.getEmail(), newUser.getProfession());
    }

    public UserDto updateUser(int id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update properties of the existing user with values from the updated user
            existingUser.setUserId(id);
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            existingUser.setDob(updatedUser.getDob());
            existingUser.setProfession(updatedUser.getProfession());
            User newUser = userRepository.save(existingUser);
            return new UserDto(newUser.getUserId(), newUser.getUserName(), newUser.getEmail(), newUser.getProfession());
        } else {
            return null;
        }
    }

}
