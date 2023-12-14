package com.proyect.apidatingassi.service.inplementation;

import com.proyect.apidatingassi.controller.dto.MesesDto;
import com.proyect.apidatingassi.service.GenericService;
import com.proyect.apidatingassi.util.CadenaUtils;
import com.proyect.apidatingassi.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenericServiceImpl implements GenericService {
    @Override
    public List<MesesDto> getMesesDtoList() {
        List<MesesDto> mesesDtos = new ArrayList<>();
        for (int cont = 1; cont < 13; cont++) {
            mesesDtos.add(new MesesDto(String.valueOf(cont), CadenaUtils.toMayusculas(DateUtil.getMonthsByItem(cont))));
        }
        return mesesDtos;
    }
}
