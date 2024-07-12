package com.arthurticianeli.petsfriends.applications.authentication.service;

import com.arthurticianeli.petsfriends.applications.authentication.domain.AccessToken;
import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;
import com.arthurticianeli.petsfriends.exceptions.DuplicatedTupleException;
import com.arthurticianeli.petsfriends.applications.authentication.domain.services.IUserService;
import com.arthurticianeli.petsfriends.applications.authentication.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public AccessToken save(User user) {
        var possibleUser = getByEmail(user.getEmail());
        if (possibleUser != null) {
            throw new DuplicatedTupleException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        var user = getByEmail(email);
        if (user == null) {
            return null;
        }

        boolean matches = passwordEncoder.matches(password, user.getPassword());

        if (!matches) {
            return null;
        }

        return jwtService.generateToken(user);
    }
}
