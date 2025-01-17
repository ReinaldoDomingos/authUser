package com.ead.authUser.service.impl;

import com.ead.authUser.UserRepository;
import com.ead.authUser.dtos.UserRecordDto;
import com.ead.authUser.enums.UserStatus;
import com.ead.authUser.enums.UserType;
import com.ead.authUser.exceptions.NotFoundException;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID id) {
        Optional<UserModel> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new NotFoundException("Error: User not found");
        }

        return userOptional;
    }

    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    @Override
    public UserModel registerUser(UserRecordDto userRecordDto) {
        var userModel = new UserModel();

        BeanUtils.copyProperties(userRecordDto, userModel);

        userModel.setStatus(UserStatus.ACTIVE);
        userModel.setType(UserType.USER);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return userRepository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserModel updateUser(UserModel user, UserRecordDto userRecordDto) {
        user.setFullName(userRecordDto.fullName());
        user.setPhoneNumber(userRecordDto.phoneNumber());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return userRepository.save(user);
    }

    @Override
    public UserModel updatePassword(UserModel user, UserRecordDto userRecordDto) {
        user.setPassword(userRecordDto.password());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return userRepository.save(user);
    }

    @Override
    public UserModel updateImage(UserModel user, UserRecordDto userRecordDto) {
        user.setImageUrl(userRecordDto.imageUrl());
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return userRepository.save(user);
    }

}
