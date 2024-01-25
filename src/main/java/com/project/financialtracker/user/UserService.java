package com.project.financialtracker.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
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

    public UserDto registerUser(UserRegistrationDto registrationDto, MultipartFile imageFile) throws IOException {
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        String imageUrl = saveImageLocally(imageFile);
        System.out.println("ImageUrl---->"+ imageUrl);
        User newUser = new User();
        newUser.setUserName(registrationDto.getUserName());
        newUser.setEmail(registrationDto.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setDob(registrationDto.getDob());
        newUser.setProfession(registrationDto.getProfession());
        newUser.setImageUrl(imageUrl);
        User user = userRepository.save(newUser);

        return new UserDto(user.getUserId(), user.getUserName(), user.getEmail(), user.getProfession());
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

    public String saveImageLocally(MultipartFile multipartFile) throws IOException {
//        final String UPLOAD_DIRS = "C:\\Users\\kalya\\OneDrive\\Desktop\\NCHL\\finacialTracker\\financial-tracker\\src\\main\\resources\\static\\image";
        final String UPLOAD_DIRS = new ClassPathResource("static/image").getFile().getAbsolutePath();
        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIRS + File.separator + multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
       }catch (Exception e)
       {
           e.printStackTrace();
       }
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(Objects.requireNonNull(multipartFile.getOriginalFilename())).toUriString();
    }
}
