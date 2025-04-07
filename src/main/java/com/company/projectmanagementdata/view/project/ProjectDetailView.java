package com.company.projectmanagementdata.view.project;

import com.company.projectmanagementdata.datatype.ProjectLabels;
import com.company.projectmanagementdata.entity.Project;
import com.company.projectmanagementdata.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;

import java.util.List;

@Route(value = "projects/:id", layout = MainView.class)
@ViewController(id = "Project.detail")
@ViewDescriptor(path = "project-detail-view.xml")
@EditedEntityContainer("projectDc")
public class ProjectDetailView extends StandardDetailView<Project> {

    @ViewComponent
    private TypedTextField<ProjectLabels> labelsField;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {

        labelsField.setReadOnly(false);
        event.getEntity().setLabels(new ProjectLabels(List.of("bug", "task")));

    }
    
    
}