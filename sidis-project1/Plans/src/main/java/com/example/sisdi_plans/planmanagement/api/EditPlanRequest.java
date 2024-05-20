package com.example.sisdi_plans.planmanagement.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditPlanRequest {

    @Size(min = 1, max = 2048)
    private String description;

    @Min(0)
    private String numberOfMinutes;

    @Size(min = 1, max = 16)
    private String musicSuggestions;

    @Min(0)
    private String musicCollections;

}
