package com.example.service.implementation.user;

import com.example.entity.enums.AccountRoles;
import com.example.entity.user.Role;
import com.example.entity.user.User;
import com.example.repository.account.RoleRepository;
import com.example.repository.account.UserRepository;
import com.example.repository.xml.XmlRepository;
import com.example.service.interfaces.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final XmlRepository xmlRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, XmlRepository xmlRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.xmlRepository = xmlRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        Role role = roleRepository.findByName(AccountRoles.USER.toString());
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        user = userRepository.save(user);
        xmlRepository.log(user);
        return user;
    }
}
