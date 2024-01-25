package com.project.financialtracker.user;

import com.project.financialtracker.token.JwtService;
import com.project.financialtracker.token.TokenResponse;
import com.project.financialtracker.utils.CustomException;
import com.project.financialtracker.utils.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


@RestController
@RequestMapping("api/auth")
@Tag(name = "Auth Controller", description = "This is Auth api for authentication")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Operation(
            summary = "create new user",
            description = "this is a user api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success | OK"),
            @ApiResponse(responseCode = "401", description = "Not authorized"),
            @ApiResponse(responseCode = "201", description = "new user is created")

    })
    @PostMapping("/user/register")
    public ResponseEntity<ResponseWrapper<UserDto>> insertUser(@Valid @ModelAttribute UserRegistrationDto registrationDto, @RequestParam("image")MultipartFile image) {
        ResponseWrapper<UserDto> response = new ResponseWrapper<>();
        try {
            if(image.isEmpty()){
                response.setSuccess(false);
                response.setStatusCode(HttpStatus.NO_CONTENT.value());
                response.setMessage("Image Not Found, Upload Image");
                response.setResponse(null);
                return ResponseEntity.ok(response);
            }
            if (!Objects.equals(image.getContentType(), "image/jpeg")){
                response.setSuccess(false);
                response.setStatusCode(HttpStatus.NO_CONTENT.value());
                response.setMessage("Only JPG and JPEG is supported");
                response.setResponse(null);
                return ResponseEntity.ok(response);
            }
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("User registered successfully");
            response.setResponse(userService.registerUser(registrationDto, image));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<TokenResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            // Attempt to authenticate the user using the provided credentials
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            // If the authentication is successful, generate a JWT token
            if (authentication.isAuthenticated()) {
                //The authenticated user is represented by a Principal object, which typically includes details like the username.
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                User user = userService.getUserByUserName(customUserDetails.getUsername());
                String token = jwtService.generateToken(user);
                final Cookie cookie = new Cookie("auth", token);
                cookie.setSecure(false);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(50400);
                cookie.setPath("/api");
                response.addCookie(cookie);

                return ResponseEntity.ok().body(new TokenResponse(token));
            } else {
                throw new CustomException("Invalid credentials");
            }
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
