package com.proyect.apidatingassi.exception.precondition;

import com.proyect.apidatingassi.controller.dto.AssignmentParameter;
import com.proyect.apidatingassi.exception.RequestException;
import com.proyect.apidatingassi.util.NumberUtil;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PreconditionsAssignment {
    private PreconditionsAssignment() {
        throw new IllegalStateException("Utility class");
    }

    public static void checkNullBodyField(AssignmentParameter parameter) {
        if (!NumberUtil.isNumeric(parameter.getOrder())) {
            throw new RequestException("403", "The input parameter must be number.");
        }
        if (Objects.isNull(parameter.getId())) {
            throw new RequestException("403", "The input parameter cannot be null or empty.");
        }
        if (CollectionUtils.isEmpty(List.of(parameter.getIds().split(",")))) {
            throw new RequestException("403", "The parameter cannot be empty or null.");
        }
        if (Boolean.TRUE == isNumericList(parameter)) {
            throw new RequestException("403", "The parameter list must be numerical.");
        }
    }

    private static Boolean isNumericList(AssignmentParameter parameter) {
        Integer tam = parameter.getIds().split(",").length;
        List<String> resul = Arrays.stream(parameter.getIds().split(",")).filter(NumberUtil::isNumeric).toList();
        return tam != resul.size();
    }
}
