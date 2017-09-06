package com.projectstarter.ProjectStarter.service;

import com.projectstarter.ProjectStarter.model.User;
import com.projectstarter.ProjectStarter.repository.UserRepository;
import com.projectstarter.ProjectStarter.service.dto.UserListDto;
import com.projectstarter.ProjectStarter.service.transformer.UserListTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ikatlinsky
 * @since 5/12/17
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserListTransformer userListTransformer;
    private final PasswordEncoder passwordEncoder;

    @Transactional()
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<UserListDto> findAll() {
        List<User> users = userRepository.findAll();

        List<UserListDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserListDto dto = this.userListTransformer.makeDto(user);
            userDtoList.add(dto);
        }

        return userDtoList;
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id);
    }
}
