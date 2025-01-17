package com.ead.authUser.controllers;

import com.ead.authUser.dtos.UserRecordDto;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                               @JsonView(UserRecordDto.UserView.RegistrationPost.class) UserRecordDto userRecordDto) {

        if (userService.existsByUsername(userRecordDto.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken!");
        }

        if (userService.existsByEmail(userRecordDto.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken!");
        }

        UserModel userModel = userService.registerUser(userRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
