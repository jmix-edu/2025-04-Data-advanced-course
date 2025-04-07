package com.company.projectmanagementdata.datatype;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

@Converter(autoApply = true)
public class ProjectLabelsConverter implements AttributeConverter<ProjectLabels, String> {
    @Override
    public String convertToDatabaseColumn(ProjectLabels projectLabels) {
        if (projectLabels == null) {
            return "";
        }
        return Joiner.on(", ")
                .join(projectLabels.getLabels());
    }

    @Override
    public ProjectLabels convertToEntityAttribute(String dbData) {
        List<String> labels = new ArrayList<>();
        if (dbData != null) {
            labels = Splitter.on(",")
                    .omitEmptyStrings()
                    .trimResults()
                    .splitToList(dbData);
        }
        return new ProjectLabels(labels);
    }
}
