package com.company.projectmanagementdata.view.project;

import com.company.projectmanagementdata.app.ProjectsService;
import com.company.projectmanagementdata.datatype.ProjectLabels;
import com.company.projectmanagementdata.entity.Project;
import com.company.projectmanagementdata.view.main.MainView;
import com.company.projectmanagementdata.view.user.UserListView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Route(value = "projects/:id", layout = MainView.class)
@ViewController(id = "Project.detail")
@ViewDescriptor(path = "project-detail-view.xml")
@EditedEntityContainer("projectDc")
public class ProjectDetailView extends StandardDetailView<Project> {

    @ViewComponent
    private TypedTextField<ProjectLabels> labelsField;
    @Autowired
    private ProjectsService projectsService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Validator validator;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<Project> event) {

        labelsField.setReadOnly(false);
        event.getEntity().setLabels(new ProjectLabels(List.of("bug", "task")));

    }

    @Subscribe(id = "commitWithBeanValidationButton", subject = "clickListener")
    public void onCommitWithBeanValidationClick(final ClickEvent<JmixButton> event) {
        try {
            projectsService.save(getEditedEntity());
            close(StandardOutcome.CLOSE);
        } catch (ConstraintViolationException ex) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                sb.append(violation.getMessage()).append("\n");
            }

            notifications.create(sb.toString())
                    .withType(Notifications.Type.WARNING)
                    .withPosition(Notification.Position.TOP_START)
                    .show();

        }
    }

    @Subscribe(id = "performBeanValidationButton", subject = "clickListener")
    public void onPerformBeanValidationButtonClick(final ClickEvent<JmixButton> event) {
        Set<ConstraintViolation<Project>> violations = validator.validate(getEditedEntity());

        showViolations(violations);
    }

    private void showViolations(Set<ConstraintViolation<Project>> violations) {
        StringBuilder sb = new StringBuilder();

        for (ConstraintViolation<?> violation : violations) {
            sb.append(violation.getMessage()).append("\n");
        }

        notifications.create(sb.toString())
                .withType(Notifications.Type.WARNING)
                .withPosition(Notification.Position.TOP_END)
                .show();
    }

    @Install(to = "participantsDataGrid.addAction", subject = "viewConfigurer")
    private void participantsDataGridAddActionViewConfigurer(final UserListView userListView) {
        userListView.setFilterProject(getEditedEntity());
    }


}