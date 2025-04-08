package com.company.projectmanagementdata.listener;

import com.company.projectmanagementdata.entity.Project;
import com.company.projectmanagementdata.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskEventListener {

    @Autowired
    private DataManager dataManager;

    @EventListener
    public void onTaskChangedBeforeCommit(final EntityChangedEvent<Task> event) {
        Project project;
        if (event.getType() == EntityChangedEvent.Type.DELETED) {
            Id<Object> projectId = event.getChanges().getOldReferenceId("project");
            project = (Project) dataManager.load(projectId).optional().orElse(null);
        } else {
            Task task = dataManager.load(event.getEntityId()).one();
            project = task.getProject();
        }

        if (project == null) {
            return;
        }

        int totalEfforts = project.getTasks().stream()
                .mapToInt(task -> task.getEstimatedEfforts() == null
                ? 0 : task.getEstimatedEfforts())
                .sum();

        project.setTotalEstimatedEfforts(totalEfforts);
        dataManager.save(project);
    }
}