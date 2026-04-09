package com.leoga.ecom.user.services;

import com.leoga.ecom.user.dto.UserRequest;
import com.leoga.ecom.user.dto.UserResponse;
import com.leoga.ecom.user.mappers.UserMapper;
import com.leoga.ecom.user.model.User;
import com.leoga.ecom.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KeyCloakAdminService keyCloakAdminService;

    public List<UserResponse> getAllUsers() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    public void addUser(UserRequest userRequest) {

        String token = keyCloakAdminService.getAdminAccessToken();
        String keyCloakUserId = keyCloakAdminService.createUser(token, userRequest);

        User user = userMapper.toEntity(userRequest);
        user.setKeycloakId(keyCloakUserId);

        // Role name hardcoded for testing, it could be included in UserRequest
        keyCloakAdminService.assignRealmRoleToUser(userRequest.getUserName(), "USER", keyCloakUserId);
        userRepository.save(user);
    }

    public UserResponse findById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse).orElse(null);
    }

    public boolean updateUser(String id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                        .map(existingUser -> {
                            userMapper.patchUser(updatedUserRequest, existingUser);
                            userRepository.save(existingUser);
                            return true;
                        }).orElse(false);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}
