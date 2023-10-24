package com.proyect.apidatingappus.model.entitytest;

import com.proyect.apidatingappus.model.Assignment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignmentBuilder {
    private Long id;
    private String name;
    private String description;
    private Long price;


    private AssignmentBuilder toAssignmentBuilder() {
        return AssignmentBuilder.builder()
                .id(1L)
                .name("Pelo regogido")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vulputate porta lacinia. Maecenas malesuada tempor augue vel consectetur. Mauris sed tellus et enim varius varius.")
                .price(Long.getLong("125"))
                .build();
    }

    public Assignment toAssignment() {
        AssignmentBuilder builder = toAssignmentBuilder();
        return new Assignment(builder.id, builder.name, builder.description, builder.price);
    }
}
