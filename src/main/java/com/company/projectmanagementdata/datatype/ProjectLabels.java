package com.company.projectmanagementdata.datatype;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class ProjectLabels implements Serializable {

    private final List<String> labels;

    public ProjectLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getLabels() {
        return Collections.unmodifiableList(labels);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof ProjectLabels that)) return false;

        return labels.equals(that.labels);
    }

    @Override
    public int hashCode() {
        return labels.hashCode();
    }
}
