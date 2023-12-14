package com.proyect.apidatingassi.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignmentParameter {
    private String order;
    private Long id;
    private String ids;

    private AssignmentParameter toEntity() {
        return AssignmentParameter
                .builder()
                .order("0")
                .id(0L)
                .ids("1,2")
                .build();
    }

    public AssignmentParameter toEditOrder(String order) {
        AssignmentParameter parameter = toEntity();
        return new AssignmentParameter(order, parameter.getId(), parameter.ids);
    }

    public AssignmentParameter toEditId(Long id) {
        AssignmentParameter parameter = toEntity();
        return new AssignmentParameter(parameter.order, id, parameter.ids);
    }

    public AssignmentParameter toEditListId(String ids) {
        AssignmentParameter parameter = toEntity();
        return new AssignmentParameter(parameter.order, parameter.getId(), ids);
    }
}
