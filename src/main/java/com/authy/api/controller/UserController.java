package com.authy.api.controller;


import com.authy.api.model.UserModel;
import com.authy.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserModel user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("validatePassword")
    public ResponseEntity<?> validatePassword(@RequestBody UserModel user) {
        Optional<UserModel> userData = repository.findByLogin(user.getLogin());
        userData.orElseThrow();
        boolean responseEncode = encoder.matches(user.getPassword(), userData.get().getPassword());
        HttpStatus status = (responseEncode) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(responseEncode);
    }
}
