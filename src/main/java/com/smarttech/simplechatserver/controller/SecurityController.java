package com.smarttech.simplechatserver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/me")
public class SecurityController {

    @GetMapping
    public String me(Authentication authentication) {
        return authentication.getName();
    }

}
