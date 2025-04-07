package com.company.projectmanagementdata.app;

import com.company.projectmanagementdata.entity.Project;
import com.company.projectmanagementdata.entity.ProjectStats;
import com.company.projectmanagementdata.entity.Task;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ProjectStatsService {
    private final DataManager dataManager;

    public ProjectStatsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<ProjectStats> fetchStats() {
        List<Project> projects = dataManager.load(Project.class).all().list();
        return projects.stream()
                .map(project -> {
                    ProjectStats stats = dataManager.create(ProjectStats.class);
                    stats.setProjectName(project.getName());

                    List<Task> tasks = project.getTasks();
                    stats.setTasksCount(tasks.size());

                    Integer estimatedEfforts = tasks.stream()
                            .map(task -> task.getEstimatedEfforts() == null
                                    ? 0
                                    : task.getEstimatedEfforts())
                            .reduce(0, Integer::sum);

                    stats.setPlannedEfforts(estimatedEfforts);
                    stats.setActualEfforts(getActualEfforts(project.getId()));
                   return stats;

                })
                .toList();
    }

    private Integer getActualEfforts(UUID id) {
        return dataManager.loadValue("select sum(te.timeSpent) from TimeEntry te " +
                        "where te.task.project.id = :projectId", Integer.class)
                .parameter("projectId", id)
                .one();
    }
}