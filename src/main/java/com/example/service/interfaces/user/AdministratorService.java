package com.example.service.interfaces.user;

import com.example.entity.user.Administrator;

import java.util.List;

public interface AdministratorService {
    List<Administrator> findAllAdmins();

    Administrator findAdminWithMinWork();

    Administrator findAdministratorByUsername(String username);

    Administrator findAdministratorById(Long id);
}
