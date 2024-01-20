package com.project.financialtracker.user;

import com.project.financialtracker.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "User Controller", description = "This is user api for user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    static final String ERROR = "Internal Server Error";

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper<User>> getUserById(@PathVariable("userId") int userId) {
        ResponseWrapper<User> response = new ResponseWrapper<>();
        try {
            if (userService.getAUserById(userId) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("User retrieved successfully");
                response.setResponse(userService.getAUserById(userId));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @Operation(
            summary = "Fetch all users",
            description = "fetches all user entities and their data from data source")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Not found"),
    })
    @GetMapping("/user")
    public ResponseEntity<ResponseWrapper<List<UserDto>>> getAllUser() {
        ResponseWrapper<List<UserDto>> response = new ResponseWrapper<>();
        try {
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Users retrieved successfully");
            response.setResponse(userService.getAllUser());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    @PutMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper<UserDto>> updateUser(@PathVariable("userId") int userId, @Valid @RequestBody User user) {
        ResponseWrapper<UserDto> response = new ResponseWrapper<>();
        try {
            if ( userService.updateUser(userId, user) != null) {
                response.setStatusCode(HttpStatus.OK.value());
                response.setSuccess(true);
                response.setMessage("User updated successfully");
                response.setResponse( userService.updateUser(userId, user));
                return ResponseEntity.ok(response);
            } else {
                response.setStatusCode(HttpStatus.NOT_FOUND.value());
                response.setMessage("User not Found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage(ERROR);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/userprofile")
    public ResponseEntity<UserProfile> userProfile(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("userId");
        String userName = (String) request.getAttribute("userName");
        String email = (String) request.getAttribute("email");
        String profession = (String) request.getAttribute("profession");
        UserProfile userProfile = new UserProfile(userId, userName, email, profession);
        return ResponseEntity.ok(userProfile);
    }
}
