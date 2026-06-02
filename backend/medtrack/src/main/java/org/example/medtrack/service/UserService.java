package org.example.medtrack.service;

import org.example.medtrack.entity.User;
import org.example.medtrack.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User register(User user) {

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        user.setRole("USER");

        return userRepository.save(user);
    }
}
