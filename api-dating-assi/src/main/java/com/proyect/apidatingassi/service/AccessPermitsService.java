package com.proyect.apidatingassi.service;

import com.proyect.apidatingassi.controller.dto.AccessPermitsDto;
import org.springframework.stereotype.Service;

@Service
public interface AccessPermitsService {
    AccessPermitsDto getAccessPermitsWithoutId();
}
