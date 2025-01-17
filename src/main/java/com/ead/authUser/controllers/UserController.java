package com.ead.authUser.controllers;

import com.ead.authUser.dtos.UserRecordDto;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> userOptional = userService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> userOptional = userService.findById(id);

        userService.delete(userOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Validated(UserRecordDto.UserView.UserPut.class)
                                             @JsonView(UserRecordDto.UserView.UserPut.class)
                                             UserRecordDto userRecordDto) {
        var userOptional = userService.findById(id);

        UserModel user = userOptional.get();

        UserModel userUpdated = userService.updateUser(user, userRecordDto);

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "id") UUID id,
                                                 @RequestBody @Validated(UserRecordDto.UserView.PasswordPut.class)
                                                 @JsonView(UserRecordDto.UserView.PasswordPut.class)
                                                 UserRecordDto userRecordDto) {
        var userOptional = userService.findById(id);

        UserModel user = userOptional.get();

        if (!user.getPassword().equals(userRecordDto.oldPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password");
        }

        userService.updatePassword(user, userRecordDto);

        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "id") UUID id,
                                              @RequestBody @Validated(UserRecordDto.UserView.ImagePut.class)
                                              @JsonView(UserRecordDto.UserView.ImagePut.class)
                                              UserRecordDto userRecordDto) {
        var userOptional = userService.findById(id);

        UserModel user = userOptional.get();

        UserModel userUpdated = userService.updateImage(user, userRecordDto);

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }
}
