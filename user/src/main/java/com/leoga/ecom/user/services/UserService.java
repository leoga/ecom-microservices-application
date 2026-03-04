package com.leoga.ecom.user.services;

import com.leoga.ecom.user.dto.AddressDTO;
import com.leoga.ecom.user.dto.UserRequest;
import com.leoga.ecom.user.dto.UserResponse;
import com.leoga.ecom.user.model.Address;
import com.leoga.ecom.user.model.User;
import com.leoga.ecom.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().
                map(this::mapToUserResponse).toList();
    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);

        userRepository.save(user);
    }

    public UserResponse findById(String id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse).orElse(null);
    }

    public boolean updateUser(String id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                        .map(existingUser -> {
                            updateUserFromRequest(existingUser, updatedUserRequest);
                            userRepository.save(existingUser);
                            return true;
                        }).orElse(false);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());

        if (null != user.getAddress()) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDTO);
        }

        return userResponse;
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (null != userRequest.getAddress()) {
            if (null == user.getAddress()) user.setAddress(new Address());
            user.getAddress().setStreet(userRequest.getAddress().getStreet());
            user.getAddress().setCity(userRequest.getAddress().getCity());
            user.getAddress().setState(userRequest.getAddress().getState());
            user.getAddress().setCountry(userRequest.getAddress().getCountry());
            user.getAddress().setZipcode(userRequest.getAddress().getZipcode());
        }
    }
}
