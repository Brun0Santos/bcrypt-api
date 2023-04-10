package com.authy.api.controller;


import com.authy.api.model.UserModel;
import com.authy.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody UserModel user) {
        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
