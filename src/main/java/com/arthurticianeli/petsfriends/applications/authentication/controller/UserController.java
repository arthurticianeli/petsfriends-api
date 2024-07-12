package com.arthurticianeli.petsfriends.applications.authentication.controller;

import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.CrendentialsRequestDto;
import com.arthurticianeli.petsfriends.applications.authentication.domain.dtos.UserRequestDto;
import com.arthurticianeli.petsfriends.applications.authentication.domain.entity.User;
import com.arthurticianeli.petsfriends.applications.authentication.domain.services.IUserService;
import com.arthurticianeli.petsfriends.exceptions.DuplicatedTupleException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        try {
            User user = new User(userRequestDto);
            var accessToken = userService.save(user);
            return ResponseEntity.ok(accessToken);
        } catch (DuplicatedTupleException e) {
            Map<String, String> response = Map.of("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody @Valid CrendentialsRequestDto crendentialsRequestDto) {
        var accessToken = userService.authenticate(crendentialsRequestDto.email(), crendentialsRequestDto.password());
        if (accessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(accessToken);
    }
}
