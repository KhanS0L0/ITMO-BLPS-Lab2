package com.example.repository;

import com.example.entity.user.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

    Administrator findAdministratorByUsername(String username);

    Administrator findAdministratorById(Long id);
}
