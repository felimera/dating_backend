package com.proyect.apidatingassi.service.inplementation;

import com.proyect.apidatingassi.controller.dto.AccessPermitsDto;
import com.proyect.apidatingassi.controller.mapper.RouterLinkMapper;
import com.proyect.apidatingassi.exception.NotFoundException;
import com.proyect.apidatingassi.model.RouterLink;
import com.proyect.apidatingassi.repository.RouterLinkRepository;
import com.proyect.apidatingassi.service.AccessPermitsService;
import com.proyect.apidatingassi.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AccessPermitsServiceImpl implements AccessPermitsService {
    private RouterLinkRepository routerLinkRepository;

    @Autowired
    public AccessPermitsServiceImpl(RouterLinkRepository routerLinkRepository) {
        this.routerLinkRepository = routerLinkRepository;
    }

    @Override
    public AccessPermitsDto getAccessPermitsWithoutId() {

        AccessPermitsDto dto = new AccessPermitsDto();
        RouterLink routerLink = routerLinkRepository
                .getRouterLinkByOne()
                .orElseThrow(() -> new NotFoundException(Constants.MESSAGE_NOT_FOUND, "801-1", HttpStatus.NOT_FOUND));
        dto.setListRouterList(Collections.singletonList(RouterLinkMapper.INSTANCE.toDto(routerLink)));

        return dto;
    }
}
