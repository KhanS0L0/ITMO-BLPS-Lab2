package com.example.service.implementation.user;

import com.example.entity.user.Administrator;
import com.example.repository.AdministratorRepository;
import com.example.service.interfaces.user.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public List<Administrator> findAllAdmins() {
        return administratorRepository.findAll();
    }

    @Override
    public Administrator findAdminWithMinWork() {
        List<Administrator> administrators = administratorRepository.findAll();
        Administrator selectedAdmin = null;

        int inspections = Integer.MAX_VALUE;
        for(Administrator administrator: administrators){
            if(administrator.getInspections().size() < inspections){
                inspections = administrator.getInspections().size();
                selectedAdmin = administrator;
            }
        }
        return selectedAdmin;
    }

    @Override
    public Administrator findAdministratorByUsername(String username) {
        return administratorRepository.findAdministratorByUsername(username);
    }

    @Override
    public Administrator findAdministratorById(Long id) {
        return administratorRepository.findAdministratorById(id);
    }
}
