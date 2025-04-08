package com.company.projectmanagementdata.validation.validDates;

import com.company.projectmanagementdata.entity.Project;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class ValidDatesProjectValidator implements ConstraintValidator<ValidDatesProject, Project> {


    @Override
    public boolean isValid(Project project, ConstraintValidatorContext context) {
        if (project == null) {
            return false;
        }

        LocalDateTime start = project.getStartDate();
        LocalDateTime end = project.getEndDate();

        if (start == null || end == null) {
            return true;
        }

        return start.isBefore(end);
    }
}
