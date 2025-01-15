package com.ead.authUser.service.impl;

import com.ead.authUser.UserRepository;
import com.ead.authUser.exceptions.NotFoundException;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
