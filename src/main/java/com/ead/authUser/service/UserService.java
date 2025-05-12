package com.ead.authUser.service;

import com.ead.authUser.dtos.UserRecordDto;
import com.ead.authUser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserModel> findAll();

    Page<UserModel> findAll(Specification<UserModel> userSpec, Pageable pageable);

    Optional<UserModel> findById(UUID id);

    void delete(UserModel userModel);

    UserModel registerUser(UserRecordDto userRecordDto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserModel updateUser(UserModel user, UserRecordDto userRecordDto);

    UserModel updatePassword(UserModel user, UserRecordDto userRecordDto);

    UserModel updateImage(UserModel user, UserRecordDto userRecordDto);
}
