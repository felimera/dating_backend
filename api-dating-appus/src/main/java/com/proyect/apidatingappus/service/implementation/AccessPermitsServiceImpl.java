package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.controller.dto.AccessPermitsDto;
import com.proyect.apidatingappus.controller.mapper.RouterLinkMapper;
import com.proyect.apidatingappus.exception.NotFoundException;
import com.proyect.apidatingappus.model.AccessPermits;
import com.proyect.apidatingappus.model.RouterLink;
import com.proyect.apidatingappus.model.complement.Rol;
import com.proyect.apidatingappus.repository.AccessPermitsRepository;
import com.proyect.apidatingappus.repository.RouterLinkRepository;
import com.proyect.apidatingappus.service.AccessPermitsService;
import com.proyect.apidatingappus.service.TipoRoleService;
import com.proyect.apidatingappus.service.UserService;
import com.proyect.apidatingappus.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccessPermitsServiceImpl implements AccessPermitsService {

    private TipoRoleService tipoRoleService;
    private UserService userService;
    private RouterLinkRepository routerLinkRepository;
    private AccessPermitsRepository accessPermitsRepository;

    @Autowired
    public AccessPermitsServiceImpl(TipoRoleService tipoRoleService, UserService userService, RouterLinkRepository routerLinkRepository, AccessPermitsRepository accessPermitsRepository) {
        this.tipoRoleService = tipoRoleService;
        this.userService = userService;
        this.routerLinkRepository = routerLinkRepository;
        this.accessPermitsRepository = accessPermitsRepository;
    }

    @Override
    public void postAccessPermits(Long idUser) {
        List<AccessPermits> accessPermitsList = new ArrayList<>();
        List<RouterLink> linkList = routerLinkRepository.getRouterLinkListUser();
        for (RouterLink routerLink : linkList) {
            AccessPermits permits = new AccessPermits();
            permits.setUser(userService.findById(idUser));
            permits.setTipoRole(tipoRoleService.getTipoRoleByAcronym(Rol.U.name()));
            permits.setRouterLink(routerLink);
            accessPermitsList.add(permits);
        }

        accessPermitsRepository.saveAll(accessPermitsList);
    }

    @Override
    public AccessPermitsDto getAccessPermitsByIdCustomer(Long idCustomer) {

        List<AccessPermits> accessPermitsList = accessPermitsRepository.getAccessPermitsByIdCustomer(idCustomer);
        if (accessPermitsList.isEmpty()) {
            throw new NotFoundException(Constants.MESSAGE_NOT_FOUND, "801-1", HttpStatus.NOT_FOUND);
        }
        List<RouterLink> linkList = accessPermitsList.stream().map(AccessPermits::getRouterLink).toList();
        AccessPermitsDto dto = new AccessPermitsDto();
        dto.setListRouterList(linkList.stream().map(RouterLinkMapper.INSTANCE::toDto).toList());
        dto.setTipoRoleAcronym(accessPermitsList.get(0).getTipoRole().getAcronym());

        return dto;
    }
}
