package com.proyect.apidatingappus.service;

import com.proyect.apidatingappus.controller.dto.AccessPermitsDto;
import org.springframework.stereotype.Service;

@Service
public interface AccessPermitsService {
    void postAccessPermits(Long idUser);

    AccessPermitsDto getAccessPermitsByIdCustomer(Long idCustomer);
}
