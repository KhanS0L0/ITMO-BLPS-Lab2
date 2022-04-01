package com.example.controller.controllers;

import com.example.dto.RegistrationDTO;
import com.example.service.interfaces.registration.RegistrationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/account")
@Api(tags = {"accounts"}, description = "Регистрация аккаунтов")
public class AccountController {

    private final RegistrationService registrationService;

    @Autowired
    public AccountController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @ApiOperation(value = "Регистрация пользователя")
    @PostMapping(path = "/signUp", produces = "application/json")
    public ResponseEntity registration(@RequestBody RegistrationDTO dto) throws Exception {
        registrationService.signUp(dto);
        return ResponseEntity.ok().build();
    }
}
