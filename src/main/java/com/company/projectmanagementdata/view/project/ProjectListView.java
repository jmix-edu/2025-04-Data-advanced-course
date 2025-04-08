package com.company.projectmanagementdata.view.project;

import com.company.projectmanagementdata.datatype.ProjectLabels;
import com.company.projectmanagementdata.entity.Project;
import com.company.projectmanagementdata.entity.User;
import com.company.projectmanagementdata.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route(value = "projects", layout = MainView.class)
@ViewController(id = "Project.list")
@ViewDescriptor(path = "project-list-view.xml")
@LookupComponent("projectsDataGrid")
@DialogMode(width = "64em")
public class ProjectListView extends StandardListView<Project> {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;

    @Autowired
    private CurrentAuthentication currentAuthentication;
    @ViewComponent
    private CollectionContainer<Project> projectsDc;

    @Subscribe(id = "dmCreate", subject = "clickListener")
    public void onDmCreateClick(final ClickEvent<JmixButton> event) {
        Project project = dataManager.create(Project.class);
        project.setName("Project " + RandomStringUtils.randomAlphabetic(5));

        User user = (User) currentAuthentication.getUser();
        project.setManager(user);

        // Bean validation is invoked while saving entity instance
        project.setLabels(new ProjectLabels(List.of("task", "enhancement", "bug")));

        Project saved = unconstrainedDataManager.save(project);
        projectsDc.getMutableItems().add(saved);
    }
}